package advent.solutions

object Abstraction {

  // Implement this function
  def identity[A](a: A): A = ???

  // Generaize this for any two inputs
  def pair(i: Int, b: Boolean): (Int, Boolean) = (i, b)

  // Generalise this for any tuple
  def first(ab: (Int, Boolean)): Int = ab._1

  // Generalize this for any list
  def head(is: List[Boolean]): Option[Boolean] = {
    is match {
      case Nil    => None
      case i :: _ => Some(i)
    }
  }

  // Generalize this for any option
  def getOrElse(o: Option[Boolean], b: Boolean): Boolean = {
    o match {
      case None    => b
      case Some(v) => v
    }
  }

  // Generalize this for any option
  def fold(o: Option[Int], f: Int => Boolean, b: Boolean): Boolean = {
    o match {
      case None    => b
      case Some(v) => f(v)
    }
  }

  // Implement this function
  def option[A]: Option[A] = ???

  // Implement this function
  def list[A]: List[A] = ???

}
