package exercise1

import scala.util.Try
import exercise1.Preprocess._

object InputKeyboard {

  def startInput(): String = {
    var condition = false
    var input = ""
    while (condition != true) {
      println("Press 1 to search an hotel by name")
      println("Press 2 to search an hotel 3km near to a given coordinate")
      println("Insert \"exit\" to close the program")
      input = normalizeValue(scala.io.StdIn.readLine())
      if (input == "1" || input == "2" || input == "exit") {
        condition = true
        return input
      } else {
        println("Wrong input!")
      }
    }
    input
  }

  def getHotelName(): String = {
    var condition = false
    var input = ""
    while (condition != true) {
      println("Insert hotel name")
      println("Insert \"back\" to change search method")
      input = normalizeValue(scala.io.StdIn.readLine())
      if (!input.isEmpty || input == "back") {
        condition = true
        return input
      } else {
        println("Wrong input!")
      }
    }
    input
  }

  def getCoordinates(): (Double, Double) = {
    var condition = false
    var conditionLong = false
    var lat = ""
    var long = ""

    while (condition != true) {
      println("Insert latitude")
      lat = normalizeValue(scala.io.StdIn.readLine())
      if (!lat.isEmpty && Try(lat.toDouble).isSuccess) {
        condition = true
        while (conditionLong != true) {
          println("Insert longitude")
          long = normalizeValue(scala.io.StdIn.readLine())
          if (!long.isEmpty && Try(long.toDouble).isSuccess) {
            conditionLong = true
          }else {
            println("Wrong input!")
          }
        }
      } else {
        println("Wrong input!")
      }
    }
    (lat.toDouble, long.toDouble)
  }
}
