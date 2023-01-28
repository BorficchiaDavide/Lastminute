package exercise2

import org.apache.spark.sql.SparkSession
import org.scalatest.flatspec.AnyFlatSpec
import org.apache.spark.sql.types._
import org.apache.spark.sql.Row

class Exerc2 extends AnyFlatSpec with Comparing with Exercise2 {

  "created_at value" should "be the oldest" in {
    val spark: SparkSession = SparkSession.builder().master("local[*]").getOrCreate()

    import spark.implicits._

    val inputDf = spark.sparkContext.parallelize(Seq(
      ("1","hawy","at","desktop",1.09,"2018-08-27 09:46:08"),
      ("1","hawy","at","desktop",1.09,"2018-08-01 09:46:08"),
      ("1","hawy","at","mobile",2.09,"2018-08-01 10:46:08")
    )).toDF("property_id","ota_code","pos","device","value","created_at")

    val actual = getOldestCreatedAt(inputDf)

    val expected =spark.sparkContext.parallelize(Seq(
      ("1","hawy","at","desktop",1.09,"2018-08-01 09:46:08"),
      ("1","hawy","at","mobile",2.09,"2018-08-01 10:46:08")
    )).toDF("property_id","ota_code","pos","device","value","created_at")

    assert(dataframesAreEquals(actual, expected))
  }

  "schema" should "contains hs_id column" in {
    val spark: SparkSession = SparkSession.builder().master("local[*]").getOrCreate()

    import spark.implicits._

    val orderedDf = spark.sparkContext.parallelize(Seq(
      ("117989","otel","at","desktop",1.09,"2018-08-27 09:46:08"),
      ("2","test","ar","mobile",2.09,"2018-08-01 10:46:08")
    )).toDF("property_id","ota_code","pos","device","value","created_at")

    val matchingDf = spark.sparkContext.parallelize(Seq(
      (15519,150005845,"otel","117989"),
      (15450,150005845,"htqk","131543")
    )).toDF("id","hs_id","ota_code","property_id")

    val actual = joinOrderedWithMatching(orderedDf, matchingDf)
    actual.show(false)

    val schema = StructType( Array(
      StructField("ota_code", StringType, true),
      StructField("property_id", StringType, true),
      StructField("pos", StringType, true),
      StructField("device", StringType, true),
      StructField("value", DoubleType, true),
      StructField("created_at", StringType, true),
      StructField("hs_id", IntegerType, true),
    ))

    val data = Seq(
      ("otel","117989","at","desktop",1.09,"2018-08-27 09:46:08",150005845),
      ("test","2","ar","mobile",2.09,"2018-08-01 10:46:08", null))
    val rdd = spark.sparkContext.parallelize(data)
    val rowsRDD = rdd.map(x => Row(x._1, x._2, x._3, x._4, x._5, x._6, x._7))

    val expected = spark.createDataFrame(rowsRDD,schema)

    assert(dataframesAreEquals(actual, expected))
  }

  "metrics" should "be equals" in {
    val spark: SparkSession = SparkSession.builder().master("local[*]").getOrCreate()

    import spark.implicits._

    val joinedDf = spark.sparkContext.parallelize(Seq(
      ("103061","amom","at","desktop",0.30,"2018-08-27 09:46:08", 690046124),
      ("103062","amom","at","desktop",1.30,"2018-08-26 09:46:08", 690046124),
      ("103063","amom","at","desktop",1.00,"2018-08-25 09:46:08", 690046124)
    )).toDF("property_id","ota_code","pos","device","value","created_at","hs_id")

    val actual = getMetrics(joinedDf)

    val expected =spark.sparkContext.parallelize(Seq(
      (690046124,"at","desktop",0.8666666666666667,1.3,0.3)
    )).toDF("hs_id","pos","device","avg_value","max_value","min_value")

    assert(dataframesAreEquals(actual, expected))
  }
}