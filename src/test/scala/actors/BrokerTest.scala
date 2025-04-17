package actors

import akka.actor.{ActorRef, ActorSystem}
import akka.cluster.pubsub.DistributedPubSub
import akka.testkit.{ImplicitSender, TestKit}
import actors.Broker.{AddTransaction, Clear, GetTransactions}
import blockchain.Transaction
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers

import scala.concurrent.duration._

class BrokerTest(sys: ActorSystem) extends TestKit(sys)
  with ImplicitSender
  with Matchers
  with AnyFlatSpecLike
  with BeforeAndAfterAll {

  def this() = this(ActorSystem("broker-test"))
  val mediator: ActorRef = DistributedPubSub(this.system).mediator

  override def afterAll(): Unit = {
    shutdown(system)
  }

  "A Broker Actor" should "start with an empty list of transactions" in {
    val broker = system.actorOf(Broker.props)

    broker ! GetTransactions
    expectMsg(500.millis, List())
  }

  it should "return the correct list of added transactions" in {
    val broker = system.actorOf(Broker.props)
    val transaction1 = Transaction("A", "B", 100)
    val transaction2 = Transaction("C", "D", 1000)

    broker ! AddTransaction(transaction1)
    broker ! AddTransaction(transaction2)

    broker ! GetTransactions
    expectMsg(500.millis, List(transaction2, transaction1))
  }

  it should "clear the transaction lists when requested" in {
    val broker = system.actorOf(Broker.props)
    val transaction1 = Transaction("A", "B", 100)
    val transaction2 = Transaction("C", "D", 1000)

    broker ! AddTransaction(transaction1)
    broker ! AddTransaction(transaction2)

    broker ! Clear

    broker ! GetTransactions
    expectMsg(500.millis, List())
  }

}
