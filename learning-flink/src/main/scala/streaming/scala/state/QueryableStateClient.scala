//package streaming.scala.state
//
//import java.util.concurrent.CompletableFuture
//
//import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
//import org.apache.flink.api.common.JobID
//import org.apache.flink.api.common.typeinfo.{TypeHint, TypeInformation, Types}
//import org.apache.flink.queryablestate.client.QueryableStateClient
//
//object QueryableStateClient {
//
//  def main(args: Array[String]): Unit = {
//    val tmHostname: String = "localhost"
//    val proxyPort: Int = 9069
//    val jobId: String = "d1227b1a350d952c372re4c886d2re243"
//    val key: Integer = 5
//
//    val client: QueryableStateClient = new QueryableStateClient(tmHostname, proxyPort)
//
//    val valueDescriptor: ValueStateDescriptor[Long] = new ValueStateDescriptor[Long]("leastValue", TypeInformation.of(new TypeHint[Long]() {}))
//
//    val resultFuture: CompletableFuture[ValueState[Long]] = client.getKvState(
//      JobID.fromHexString(jobId),
//      "leastQueryValue",
//      key,
//      Types.INT,
//      valueDescriptor)
//    //从resultFuture等待返回结果
////    resultFuture.thenAccept(response => {
////      try
////        val res = response.value()
////      catch {
////        case e: Exception =>
////          e.printStackTrace()
////      }
////    })
//  }
//
//}
