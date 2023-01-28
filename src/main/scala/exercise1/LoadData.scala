package exercise1

import com.github.tototoshi.csv.{CSVReader, DefaultCSVFormat}

object LoadData {
  implicit object format extends DefaultCSVFormat {
    override val delimiter: Char = ','
    override val quoteChar: Char = '"'
    override val escapeChar: Char = '\\'
  }

  def loadCsv(filePath: String): List[Map[String, String]] = {
    val reader = CSVReader.open(filePath)(format)
    reader.allWithHeaders()
  }

}
