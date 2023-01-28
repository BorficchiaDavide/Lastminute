package exercise1

import scalikejdbc._

case class OTA(id:Int,
               ota_id:Int,
               hotel_id:Int,
               name:String,
               address:String,
               zip_code:Int,
               city:String,
               country:String,
               latitude:Double,
               longitude:Double,
               discardMotivation: String
              )

object OTA extends SQLSyntaxSupport[OTA] {
  override val tableName = "otas"
  def apply(rs: WrappedResultSet) = new OTA(
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
}