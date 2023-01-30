package exercise1

import exercise1.Preprocess._
import exercise1.LoadData._
import exercise1.InputKeyboard._
import exercise1.SearchMethods._

object Main {
  def main(args: Array[String]) {
    val queryRange = 3
    val filePath = "src/main/resources/exercise1/data.csv"
    val data = loadCsv(filePath)

    // processing raw data
    val rawOtas = convertToOTA(data)

    val otasStringCleaned = normalizeStringValues(rawOtas)

    val mappedOtas = mapDiscardMotivation(otasStringCleaned)

    //if we want to separate the data discarded data between the good data we can use this method and then save them
    // in two different talbes
//    val otas = getCorrectElements(mappedOtas)

    // if we want do some operation on the discardedOtas we can get them with the getDiscardedElements method
//      val discardedOtas = getDiscardedElements(mappedOtas)
//      discardedOtas.foreach(println(_))

    DbUtils.createOtaTable()
//      we are going to save all the otas (discarded and not)
    DbUtils.insertValues(mappedOtas)

// latitude for test
//42.700589000000000000
//longitude for test
//23.314555000000000000

    var userInput: String = ""
    while (userInput != "exit") {
      userInput = startInput()
      if (userInput == "1") {
        val hotelNameToSearch = getHotelName()
        if (hotelNameToSearch != "back") {
          println("The result is:")
          getInfoByName(hotelNameToSearch).foreach(x => println(x.ota_id + " " + x.hotel_id))
        }
      } else if (userInput == "2") {
        val coord = getCoordinates()
        val otasWithGoodCoord = getElementsWithGoodCoordinates
        println(s"otas with good coordinates: ${otasWithGoodCoord.length}")
        otasWithGoodCoord
          .filter(ota => isInTheRadius(coord._1, coord._2, ota.latitude, ota.longitude, queryRange))
          .foreach(println(_))
      }
    }
  }
}
