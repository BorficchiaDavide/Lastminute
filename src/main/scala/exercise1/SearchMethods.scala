package exercise1

object SearchMethods {

  def isInTheRadius(lat1: Double, lon1: Double, lat2: Double, lon2: Double, distance: Double) = {
    val R = 6371 // Radius of the earth in km
    val dLat = deg2rad(lat2 - lat1)
    val dLon = deg2rad(lon2 - lon1)
    val a =
      Math.sin(dLat / 2) * Math.sin(dLat / 2) +
        Math.cos(deg2rad(lat1)) * Math.cos(deg2rad(lat2)) *
        Math.sin(dLon / 2) * Math.sin(dLon / 2)
    val c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a))
    val d = R * c // Distance in km
    d < distance
  }

  def deg2rad(deg: Double): Double = deg * (Math.PI / 180)

  def getInfoByName(name: String): List[OTA] =
    DbUtils.getElementsByName(name)

  def getElementsWithGoodCoordinates: List[OTA] =
    DbUtils.getElementsWithGoodCoordinates()

}
