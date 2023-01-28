package exercise2

import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.functions._

trait Exercise2 {

  def getOldestCreatedAt(inputDf: DataFrame): DataFrame = {
    val w = Window.partitionBy("property_id", "ota_code", "pos", "device").orderBy(asc("created_at"))
    inputDf.withColumn("row_num", row_number.over(w)).filter("row_num = 1").drop("row_num")
  }
  def joinOrderedWithMatching(orderedDf: DataFrame, matchingDf: DataFrame): DataFrame =
    orderedDf.join(matchingDf.select("ota_code", "property_id", "hs_id"), Seq("ota_code", "property_id"), "left")

  def getMetrics(inputDf: DataFrame): DataFrame = {
    inputDf
      .groupBy("hs_id", "pos", "device")
      .agg(avg("value").as("avg_value"), max("value").as("max_value"), min("value").as("min_value"))
  }

  def getClickDistribution(inputDf: DataFrame): DataFrame =
    inputDf.groupBy("hs_id", "otaCode").count()
}
