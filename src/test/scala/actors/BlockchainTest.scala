package actors

import akka.actor.ActorSystem
import akka.testkit.{ImplicitSender, TestKit}
import actors.Blockchain.{AddBlockCommand, GetChain, GetLastHash, GetLastIndex}
import blockchain.{ChainLink, EmptyChain, Transaction}
import org.scalatest.BeforeAndAfterAll
import org.scalatest.flatspec.AnyFlatSpecLike
import org.scalatest.matchers.should.Matchers
import com.typesafe.config.ConfigFactory

import scala.concurrent.duration._

class BlockchainTest(sys: ActorSystem) extends TestKit(sys)
  with ImplicitSender
  with Matchers
  with AnyFlatSpecLike
  with BeforeAndAfterAll {

  def this() = this(ActorSystem("blockchain-test", ConfigFactory.load("test")))

  override def afterAll(): Unit = {
    shutdown(system)
  }

  "A Blockchain Actor" should "correctly add a new block" in {
    val blockchain = system.actorOf(Blockchain.props(EmptyChain, "test"))

    within(1.second) {
      blockchain ! GetChain
      expectMsg(EmptyChain)

      blockchain ! GetLastIndex
      expectMsg(0)

      blockchain ! GetLastHash
      expectMsg("1")

      val transactions = List(Transaction("a", "b", 1L))
      val proof = 1L
      blockchain ! AddBlockCommand(transactions, proof, System.currentTimeMillis())
      expectMsg(1)

      blockchain ! GetLastIndex
      expectMsg(1)

      blockchain ! GetChain
      expectMsgType[ChainLink]
    }
  }

  it should "correctly recover from a snapshot" in {
    val blockchain = system.actorOf(Blockchain.props(EmptyChain, "test"))

    within(1.second) {
      blockchain ! GetLastIndex
      expectMsg(1)

      blockchain ! GetChain
      val ack = expectMsgType[ChainLink]

      ack.values.head.sender shouldBe "a"
    }
  }
}
