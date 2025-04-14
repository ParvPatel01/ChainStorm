package actors

import akka.actor.{Actor, ActorLogging, Props}
import Miner.*
import akka.actor.Status.{Failure, Success}
import exception.{InvalidProofException, MinerBusyException}
import proofOfWork.ProofOfWork

import scala.concurrent.Future

object Miner {
  sealed trait MinerMessage
  case class Validate (hash: String, proof: Long) extends MinerMessage
  case class Mine(hash: String) extends MinerMessage
  case object Ready extends MinerMessage

  val props: Props = Props(new Miner)
}

class Miner extends Actor with ActorLogging {
  import context._

  def validate: Receive = {
    case Validate(hash, proof) => {
      log.info(s"Validating proof $proof")
      if(ProofOfWork.validProof(hash, proof)) {
        log.info("Proof is valid!")
        sender() ! Success
      } else {
        log.info("Proof is not valid!")
        sender() ! Failure(new InvalidProofException(hash, proof))
      }
    }
  }

  def ready: Receive = validate.orElse{
    case Mine(hash) => {
      log.info(s"Mining hash $hash ...")
      val proof = Future {
        ProofOfWork.proofOfWorkAlgo(hash)
      }
      sender() ! proof
      become(busy)
    }
    case Ready => {
      log.info("Ready to Mine !!")
      sender() ! Success("OK")
    }
  }

  def busy: Receive = validate.orElse {
    case Mine(_) => {
      log.info("Already Mining!!")
      sender() ! Failure(new MinerBusyException("Miner Is Busy!!"))
    }
    case Ready => {
      log.info("Ready To Mine New Block!!")
      become(ready)
    }
  }

  override def receive: Receive = {
    case Ready => become(ready)
  }
}