package blockchain

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should.Matchers

class ChainTest extends AnyFlatSpec with Matchers {

  "A Chain" should "be correctly built" in {
    val transaction = new Transaction("me", "you", 1)

    val empty = Chain()
    empty.index should be (0)

    val link = new ChainLink(1, 1, List(transaction), "abc", tail = empty)
    val justOne = Chain(link)
    justOne.index should be (1)

    val link2 = new ChainLink(2, 1, List(transaction), "abc", tail = link)
    val justTwo = Chain(link2)
    justTwo.index should be (2)
  }
}