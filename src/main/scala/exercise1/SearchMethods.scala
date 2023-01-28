package exercise1

object SearchMethods {

  def isInTheRadius(lat1: Double, lon1: Double, lat2: Double, lon2: Double, distance: Double) = {
      var R = 6371 // Radius of the earth in km
      var dLat = deg2rad(lat2-lat1)
      var dLon = deg2rad(lon2-lon1)
      var a =
        Math.sin(dLat/2) * Math.sin(dLat/2) +
          Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
            Math.sin(dLon/2) * Math.sin(dLon/2)

      var c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a))
      var d = R * c // Distance in km
      if (d < distance) true else false
    }

  private def deg2rad(deg: Double): Double = {
    val result = deg * (Math.PI/180)
    result
  }

  private def rad2deg(rad: Double): Double = {
    val result = (rad * 180.0 / Math.PI)
    result
  }

  def getInfoByName(name: String): List[OTA] = {
    DbUtils.getElementsByName(name)
  }

  def getElementsWithGoodCoordinates(): List[OTA] = {
    DbUtils.getElementsWithGoodCoordinates()
  }

}
