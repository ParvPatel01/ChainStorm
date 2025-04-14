package actors

import blockchain.Transaction
import blockchain.Chain
import actors.Blockchain.*
import akka.actor.{ActorLogging, Props}
import akka.persistence.PersistentActor

object Blockchain {
  sealed trait BlockchainEvent
  case class AddBlockEvent(transactions: List[Transaction], proof: Long, timestamp: Long) extends BlockchainEvent

  sealed trait BlockchainCommand
  case class AddBlockCommand(transactions: List[Transaction], proof: Long, timestamp: Long) extends BlockchainCommand
  case object GetChain extends BlockchainCommand
  case object GetLastHash extends BlockchainCommand
  case object GetLastIndex extends BlockchainCommand

  case class State(chain: Chain)

  def props(chain: Chain, nodeId: String): Props = Props(new Blockchain(chain, nodeId))
}

class Blockchain(chain: Chain, nodeId: String) extends PersistentActor with ActorLogging {
  ???
}