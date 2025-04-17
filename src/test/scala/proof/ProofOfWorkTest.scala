package proof

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers
import proofOfWork.ProofOfWork

class ProofOfWorkTest extends AnyFlatSpec with Matchers {

  "Validation of proof" should "correctly validate proofs" in {
    val lastHash = "1"
    val correctProof = 7178
    val wrongProof = 0

    ProofOfWork.validProof(lastHash, correctProof) should be (true)

    ProofOfWork.validProof(lastHash, wrongProof) should be (false)
  }
}
