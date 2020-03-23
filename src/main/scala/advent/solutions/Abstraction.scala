package advent.solutions

object Abstraction {

  // Implement this function
  def identity[A](a: A): A = ???

  // Generaize this for any two inputs
  // The following calls should compile:
  // pair("apple", 1)
  // pair(true, false)
  def pair(i: Int, b: Boolean): (Int, Boolean) = (i, b)

  // Generalise this for any tuple
  // The following calls should compile:
  // first(("apple", 1))
  // first((true, false))
  def first(ab: (Int, Boolean)): Int = ab._1

  // Generalize this for any list
  // The following calls should compile
  //
  // head(List(1, 2, 3))
  // head(List(true, false))
  // head(Nil)
  def head(is: List[Boolean]): Option[Boolean] = {
    is match {
      case Nil    => None
      case i :: _ => Some(i)
    }
  }

  // Generalize this for any option
  // The following calls should compile
  //
  // getOrElse(Some(true), true)
  // getOrElse(Some("apple"), "pear")
  // getOrElse(None, 2)
  def getOrElse(o: Option[Boolean], b: Boolean): Boolean = {
    o match {
      case None    => b
      case Some(v) => v
    }
  }

  // Implement this function
  def option[A]: Option[A] = ???

  // Implement this function
  def list[A]: List[A] = ???

}
