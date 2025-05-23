import actors.Node
import akka.actor.{ActorRef, ActorSystem}
import akka.cluster.pubsub.DistributedPubSub
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route
import akka.http.scaladsl.server.RouteConcatenation._enhanceRouteWithConcatenation
import akka.stream.ActorMaterializer
import akka.util.Timeout
import api.NodeRoutes
import cluster.*
import com.typesafe.config.{Config, ConfigFactory}

import scala.concurrent.Await
import scala.concurrent.duration.*
import scala.language.postfixOps

object Server extends App with NodeRoutes {

  implicit val system: ActorSystem = ActorSystem("chainstorm")
  implicit val materializer: ActorMaterializer = ActorMaterializer()

  val config: Config = ConfigFactory.load()
  val address = config.getString("http.ip")
  val port = config.getInt("http.port")
  val nodeId = config.getString("chainstorm.node.id")

  lazy val routes: Route = statusRoutes ~ transactionRoutes ~ mineRoutes

  val clusterManager: ActorRef = system.actorOf(ClusterManager.props(nodeId), "clusterManager")
  val mediator: ActorRef = DistributedPubSub(system).mediator
  val node: ActorRef = system.actorOf(Node.props(nodeId, mediator), "node")

  Http().bindAndHandle(routes, address, port)
  println(s"Server online at http://$address:$port/")

  Await.result(system.whenTerminated, Duration.Inf)
}
