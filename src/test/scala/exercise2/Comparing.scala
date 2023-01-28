package exercise2

import org.apache.spark.sql.DataFrame

trait Comparing {

  def dataframesAreEquals(actual: DataFrame, expected: DataFrame): Boolean = {
    def compareData: Boolean = actual.except(expected).unionByName(expected.except(actual)).count().equals(0.toLong)
    def compareColumns: Boolean = actual.columns sameElements expected.columns

    List(compareData, compareColumns) reduce (_ && _)
  }
}
