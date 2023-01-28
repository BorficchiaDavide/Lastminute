package exercise1

import scala.util.Try

object Preprocess {
  private val defaultIntValue = Int.MinValue
  private val defaultCoordValue = 0.0
  def convertToOTA(rawData: List[Map[String, String]]): List[OTA] = {
    rawData.map(row =>
      OTA(
        id = Try(row("id").toInt).getOrElse(defaultIntValue),
        ota_id = Try(row("ota_id").toInt).getOrElse(defaultIntValue),
        hotel_id = Try(row("hotel_id").toInt).getOrElse(defaultIntValue),
        name = row("name"),
        address = row("address"),
        zip_code = Try(row("zip_code").toInt).getOrElse(defaultIntValue),
        city = row("city"),
        country = row("country"),
        latitude = Try(row("latitude").toDouble).getOrElse(defaultCoordValue),
        longitude = Try(row("longitude").toDouble).getOrElse(defaultCoordValue),
        discardMotivation = ""
      )
    )
  }

  def normalizeStringValues(dirtyOtas: List[OTA]): List[OTA] = {
    dirtyOtas.map { ota =>
      val newName = normalizeValue(ota.name)
      val newAddress = normalizeValue(ota.address)
      val newCity = normalizeValue(ota.city)
      val newCountry = normalizeValue(ota.address)
      ota.copy(
        name = newName,
        address = newAddress,
        city = newCity,
        country = newCountry
      )
    }.distinct
  }

  def normalizeValue(s: String): String =
    s.replaceAll("\"", "").trim.toLowerCase()

  def getCorrectElements(cleanData: List[OTA]): List[OTA] = {
    println(s"OTAs before getCorrectElements: ${cleanData.length}")
    val correctElements = cleanData.filter(row => row.discardMotivation.isEmpty)
    println(s"OTAs after getCorrectElements ${correctElements.length}")
    correctElements
  }

  def getDiscardedElements(cleanData: List[OTA]): List[OTA] = {
    val discardedElements = cleanData.filter(row => row.discardMotivation.nonEmpty)
    println(s"Discarded Otas ${discardedElements.length}")
    discardedElements
  }

  private def checkIntFields(key: String, value: Int): String =
    if (value == defaultIntValue) s"$key is not a valid Int|" else ""

  private def checkDoubleFields(key: String, value: Double): String =
    if (value == defaultCoordValue) s"$key is not a valid Double|" else ""

  private def checkStringFields(key: String, value: String): String =
    if (value.contains("do not book") || value.contains("test")) s"$key is not a valid String|" else ""

//  if the latitude,longitude or zip code are not valid, doesn't matter. i prefer to keep as much as data as possible because, for example this record could be used for the "search hotel by name"
  def mapDiscardMotivation(inputData: List[OTA]): List[OTA] = {
    inputData.map { ota =>
      val discardMessage =
        checkIntFields("id", ota.id) +
          checkIntFields("ota_id", ota.ota_id) +
          checkIntFields("hotel_id", ota.hotel_id) +
          checkStringFields("name", ota.name) +
          checkStringFields("address", ota.address) +
//        checkIntFields("zip_code", ota.zip_code) +
          checkStringFields("city", ota.city) +
          checkStringFields("country", ota.country) // +
//          checkDoubleFields("latitude", ota.latitude) +
//          checkDoubleFields("longitude", ota.longitude)
      ota.copy(
        discardMotivation = discardMessage
      )
    }
  }
}
