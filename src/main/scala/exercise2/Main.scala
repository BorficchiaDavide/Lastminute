package exercise2

import org.apache.spark.SparkConf
import org.apache.spark.sql.SparkSession

object Main extends Exercise2 {
  val conf = new SparkConf().setAppName("My App").setMaster("local")
  val spark = SparkSession.builder().config(conf).getOrCreate()

  def main(args: Array[String]) {

    val valuesDatasetPath = "src/main/resources/exercise2/values.csv"
    val matchingDatasetPath = "src/main/resources/exercise2/matching.csv"
    val clickoutDatasetPath = "src/main/resources/exercise2/clickout.csv"

    val outputPathPT3 = "src/main/resources/exercise2/output/pt4/"

    val valuesDF = spark.read.format("csv").option("header", "true").load(valuesDatasetPath)
    val matchingDf = spark.read.format("csv").option("header", "true").load(matchingDatasetPath)
    val clickoutDf = spark.read.format("csv").option("header", "true").load(clickoutDatasetPath)

//    PT 1
    val dfOrdered = getOldestCreatedAt(valuesDF)
    println("DF_ORDERED")
    dfOrdered.show(false)
//    dfOrdered.count() // 278135
//    dfOrdered.select("ota_code", "property_id").distinct().count() // 8169

//    PT 2
    val joinMatchingWithOrdered = joinOrderedWithMatching(dfOrdered, matchingDf)
    println("joinMatchingWithOrdered")
    joinMatchingWithOrdered.orderBy("ota_code", "property_id").show(false)

//    matchingDf.select("ota_code", "property_id").distinct().count() // 36840
//    joinMatchingWithOrdered.filter("hs_id is not null").count() // 36840

//    PT 3
    val computedValues = getMetrics(joinMatchingWithOrdered)
    computedValues.show(false)

//    PT 4
    computedValues.write.mode("overwrite").partitionBy("device").csv(outputPathPT3)

//    PT 5
    val clickoutDistributionDf = getClickDistribution(clickoutDf).orderBy("otaCode")
    clickoutDistributionDf.show(false)

  }

}
