package proofOfWork
import crypto.Crypto
import spray.json.*
import utils.JsonSupport.*

import scala.annotation.tailrec

object ProofOfWork {
  def proofOfWorkAlgo(lastHash: String): Long = {
    @tailrec
    def powHelper(lastHash: String, proof: Long) : Long = {
      if (validProof(lastHash, proof)) proof
      else powHelper(lastHash, proof+1)
    }

    val proof = 0
    powHelper(lastHash, proof)
  }

  def validProof(lastHash: String, proof: Long) : Boolean = {
    val guess: String = {
      (lastHash ++ proof.toString).toJson.toString
    }
    val guessProof = Crypto.sha256Hash(guess)
    (guessProof take 4) == "0000"
  }
}
