package exercise1

import scalikejdbc.{AutoSession, ConnectionPool, DBSession}
import scalikejdbc._

object DbUtils {

  // initialize JDBC driver & connection pool
  Class.forName("org.h2.Driver")
  ConnectionPool.singleton("jdbc:h2:mem:lastminute", "user", "pass")

  implicit val session: DBSession = AutoSession

  GlobalSettings.loggingSQLAndTime = LoggingSQLAndTimeSettings(
    enabled = false,
    singleLineMode = false,
    printUnprocessedStackTrace = false,
    stackTraceDepth = 15,
    logLevel = Symbol("debug"),
    warningEnabled = false,
    warningThresholdMillis = 3000L,
    warningLogLevel = Symbol("warn")
  )

  def createOtaTable() = {
    sql"""
        create table otas (
        id serial not null primary key,
        ota_id Integer not null,
        hotel_id Integer not null,

        name varchar(255),
        address varchar(255),
        zip_code integer not null,
        city varchar(255),
        country varchar(255),
        latitude double,
        longitude double,
        discardMotivation varchar(255)
      )
      """.execute.apply()
  }

  def insertValues(dataset: List[OTA]) = {
    dataset.foreach(row => sql"""
                             insert into otas (id, ota_id, hotel_id, name, address, zip_code, city, country, latitude, longitude, discardMotivation)
      values (${row.id}, ${row.ota_id}, ${row.hotel_id}, ${row.name}, ${row.address}, ${row.zip_code}, ${row.city}, ${row.country},
      ${row.latitude}, ${row.longitude}, ${row.discardMotivation})""".update.apply())
  }


  def getElementsByName(nameToSearch: String): List[OTA] = {
    val newNameToSearch = "%" + nameToSearch + "%"
    sql"select * from otas where name LIKE $newNameToSearch"
      .map(rs =>
        OTA(
          rs.int("id"),
          rs.int("ota_id"),
          rs.int("hotel_id"),
          rs.string("name"),
          rs.string("address"),
          rs.int("zip_code"),
          rs.string("city"),
          rs.string("country"),
          rs.double("latitude"),
          rs.double("longitude"),
          rs.string("discardMotivation")
        )
      )
      .list
      .apply()
  }

//  here the search method is on the coordinate so we can discard all the rows with corrupted coordinates (0.0)
  def getElementsWithGoodCoordinates(): List[OTA] = {
    sql"select * from otas where latitude != 0.0 and longitude != 0.0 "
      .map(rs =>
        OTA(
          rs.int("id"),
          rs.int("ota_id"),
          rs.int("hotel_id"),
          rs.string("name"),
          rs.string("address"),
          rs.int("zip_code"),
          rs.string("city"),
          rs.string("country"),
          rs.double("latitude"),
          rs.double("longitude"),
          rs.string("discardMotivation")
        )
      )
      .list
      .apply()
  }
}
