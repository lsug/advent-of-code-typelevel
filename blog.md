
# Advent of Code with Cats

## Day1 - Part 1

### Functional Solution

Even though the first part of the Day1 problem can be easily solved in one line of code, we purposely break it down into multiple small functions so that we can demonstrate how to apply type classes to the problem later. We first write a `fuel` function that takes in the mass of the module, divides it by three and subtracts two to find the fuel required to launch the module. 

```scala
def fuel(mass: Int): Int = mass / 3 - 2
```

In the problem, your puzzle input is a long list of all the modules on your spacecraft. To find the fuel required for each module, you could apply the `fuel` function to each module of the long list by using the `map` function. This is what we’ve done by writing a `calculateFuels` function. 

```scala
def calculateFuels(masses: List[Int]): List[Int] = masses.map(fuel)
``` 

Next, to sum up the list of fuels, we make use of the `sum` function available to `Iterable`, like `List` in our case, and write a `sumFuels` function. Finally, we put all of these small functions into a `sumOfFuel` function and solve our Part 1’s problem.

```scala
def sumFuels(fuels: List[Int]): Int = fuels.sum
``` 

The solution of Part 1 can be found in the [day-1-step-1-abstraction](https://github.com/lsug/advent-of-code-typelevel/tree/day-1-step-1-abstraction) branch. 

### Type Abstraction

Before jumping into type classes, we thought it’d be good to start by introducing generic functions and types. Have a look at the three functions below:

```scala
def returnInt(x : Int): Int = x
def returnLong(x : Long): Long = x
def returnBoolean(x : Boolean): Boolean = x
```


`returnInt` is a function that accepts and returns a value of type `Int`, whereas `returnLong` accepts and returns a value of type `Long` and finally `returnBoolean`, a value of type `Boolean`.

Although they each return a different **type** of value, they share the same pattern: return the same value that was used as its argument. Since they do the same thing fundamentally, we could write a single function that does that. In other words, we could abstract over this pattern.

```scala
def identity[A](a: A): A = a
```

The `identity[A]` function is such a function, and is called a **generic function** because it has been parameterized by type. The letter `A` in a square bracket is the **type parameter**, the syntax for a generic type in Scala. When we are generalizing a type, we tend to use letters `A`, `B` and `C` by convention.

Now, to return an `Int`, we can pass in an `Int` value to the `identity` function like this:

```scala
identity[Int](10)
```

When you run this, the output will be a value of 10.  You could also omit the `[Int]` type parameter and get the same result:

```scala
identity(10)
```

By the same logic, to return a `true` value, we simply write `identity[Boolean](true)` or just `identity(true)`.

You could have as many type parameters, also known as generic types, in a function as you like. For example, there are three different types in `tuple3[A, B, C]` below:

```scala
def tuple3[A, B, C](a: A, b: B, c: C): (A, B, C) = (a, b, c)
```

### So, what is a type? 

You can think of a type as a set of values. For concrete types like `Boolean`, we know it is a set with just two values: `true` and `false`. For `Int`, it is a set with values ranging from -2,147,483,648 to 2,147,483,647. For `Long`, it is a set of values ranging from -9,223,372,036,854,775,808 to +9,223,372,036,854,775,807. 

Earlier, we mentioned that `A` is a generic type which means we don’t know anything about the possible values that lie in set `A`.

You might be thinking, "Why is having a generic type/type parameter in a function a good thing?". Let’s go back to the `identity[A]` function.  Remember how it can take in and return an `Int`, `Long` and `Boolean`. Actually, it can do more than that. It can take in and return **any** type of value due to having a type parameter. Compared it to `returnInt`, which can only take in and return a concrete `Int` value, `identity[A]` is a lot more flexible and powerful.

In [day-1-step-1-abstraction](https://github.com/lsug/advent-of-code-typelevel/tree/day-1-step-1-abstraction) branch, there is an exercise called *Abstraction*. Can you try to make every function generic and see if you can pass all the tests.

### Type Classes and Type Class Instance

We’ve seen both types and generic functions, let’s dive into type classes. Remember the `fuel` function, can you rewrite the function and make it generic so that it can accept not only `Int` value, but other types of number like `Double`, `Long` or `Float`. In short, we are going to parameterize the `fuel` function with type. 

What did you come up with? Here is our first attempt:

```scala
def fuel[A](mass: A): A = mass / 3 - 2
```

You might have noticed our solution is not quite right because the function does not compile. If we type this function into the console, we will see the following error message:
 
```text
value / is not a member of type parameter A
```

What the error message is saying is the compiler cannot find the `/`, division operation under type `A`. Previously, when the mass parameter was a concrete type `Int`, the function compiled because `/` is defined under type `Int`. Actually, it is not only value `/` undefined, `-` (minus), `3` and `2` are also undefined under type `A`.

To make the generic `fuel[A]` function compile, first, we need to define all of these functions. We do that in a trait `Mass[A]`:

```scala
trait Mass[A] {
    def quotient(a: A, b: A): A
    def minus(a: A, b: A): A
    def two: A
    def three: A
  }
```
> *(quotient is the division of two numbers, but discards the fractional part.)* 
>

The functions in Mass are abstract (no implementation/body/content) because we don't know what A is. As a type parameter, it could be any type, but we know that these functions make sense only on numbers i.e value with type `Int`, `Long`, `Double` or `Float`, just to name a few. 

Even though we know nothing about A, we could still re-write the `fuel[A]` with these abstract functions in `Mass[A]` and deal with the implementations of these functions later when we know more about what `A` is. 

Next, we need to provide a way for `fuel[A]` to access these functions and we do that by adding another parameter to `fuel[A]`:

```scala
def fuel[A](mass: A)(M: Mass[A]): A = ???
```
Can you try and complete the function? 

What did you come up with? Here is ours:

```scala
def fuel[A](mass: A)(M: Mass[A]): A = {
    M.minus(M.quotient(mass, M.three), M.two)
  }
``` 
Now, the function compiles, let's try to find the fuel for a mass of 12 (a value with type `Int`). `fuel[Int](12)` should give us a answer of 2, right? Let's run it in the console.

```text
error: missing argument list for method fuel...
```

Oops, we forgot the extra parameter of type `Mass[A]` we added earlier. Since we are replacing type parameter `A` with type `Int`, we need to provide a value of type `Mass[Int]` to the function. However, where do we find that value from? Can you take a guess? 

It turns out we need to do a bit of DIY and construct a value of type `Mass[Int]` ourselves! Before concerning ourselves with how to construct this value, let take a step back and answer why we need this value? Let's give this value a name, `intHasMass`.

First, we know any value of type `Mass[A]` has access to the abstract functions defined in `Mass`. `intHasMass` is the same but we now have more information about this value. It is a value with a concrete type of `Mass[Int]`. Therefore, we can now complete the abstract functions in `Mass`.  

```scala
val intHasMass: Mass[Int] = new Mass[Int] {
      def quotient(a: Int, b: Int): Int = ???
      def minus(a: Int, b: Int): Int = ???
      def two: Int = ???
      def three: Int = ???
    }
``` 

By completing all the abstract functions, we provide `Int` implementations to all the functions in `Mass`. Now, as `fuel[A]` function receives a value with type `Int`, it knows how to handle it by looking up the `Mass[Int]` value, following exactly the implementations we have written down and solve the problem for us.

Can you try to implement the functions in `intHasMass`?

What did you come up with?  Here are ours:

```scala
val intHasMass: Mass[Int] = new Mass[Int] {
      def quotient(a: Int, b: Int): Int = a / b
      def minus(a: Int, b: Int): Int = a - b
      def two: Int = 2
      def three: Int = 3
    }
``` 

Now, if you run `fuel[Int](12)(intHasMass)`, you should get a value of 2. Easy!

Here is an interesting question for you. What would happen if we do this `fuel[Int](12.0)(intHasMass)`.  It is a multiple-choice question.

- (A) 2
- (B) 2.0
- (C) Type mismatch error

The answer is `(C)` because mass of 12.0 is a value of type `Double`. Although we know numerically the answer should be 2.0, the `fuel[A]` function does not know it because it does not know how to handle a value of type `Double`. It is not its fault, the compiler had helped it look for the `Mass[Double]` value but just could not find it as we did not write one.

Can you implement a value of `Mass[Double]`? Let's call it `doubleHasMass`.

Here is our implementation:

```scala
val doubleHasMass: Mass[Double] = new Mass[Double] {
     def quotient(a: Double, b: Double): Double = a / b
     def minus(a: Double, b: Double): Double = a - b
     def two: Double = 2.0
     def three: Double = 3.0
    }
``` 

Now, if you run `fuel[Double](12.0)(doubleHasMass)`, you should get a value of 2.0. Notice we put in `doubleHasMass`, not `intHasMass`.


#### Implicits

Wouldn't it be nice if we could call fuel(12) or fuel(12.0) and get the right answer without having to bother with the right type of `Mass[A]` value?

Actually, there is a way by simply adding `implicit` keyword to the parameter list of `fuel[A]`, `intHasMass` and `doubleHasMass` value.

```scala
def fuel[A](mass: A)(implicit M: Mass[A]): A = ...
implicit val intHasMass: Mass[Int] = new Mass[Int] {..}
implicit val doubleHasMass: Mass[Double] = new Mass[Double] {...}
```

By doing this, the compiler will look for a value with type `Mass[Int]` and pass in the value marked with `implicit val` every time we make a call to `fuel[Int]`. Because we put the implicit values in a `Mass` object, we need to import the object with `import Mass._`.  This brings the implicit value into scope so that the compiler can find it. 

The codes of this section can be found in [day-1-step-2](https://github.com/lsug/advent-of-code-typelevel/blob/day-1-step-2/src/main/scala/advent/solutions/Day1.scala) branch. 

You may not realise it but you have just learned to use a type class `Mass[A]` and implement two type class instances `intHasMass` and  `doubleHasMass` to solve the problem.


#### What is a type class?

In Scala, a type class is a trait that has a group of generic functions used to define some operation. The implementations of these functions depend on the type that supports the operation.

In our earlier example, `Mass` is a type class that has a group of functions like `quotient`, `minus`, `two` and `three` used to define the operations of some numeric operators that we need to find the fuel of a mass. `Int` and `Double` types support and implement these operations: `intHasMass` and `doubleHasMass`. We say `Int` and `Double` are instances of `Mass` typeclass. 

Of course, other numeric types like `Long` and `Float` can support these operations too, but we did not implement them (a.k.a write a `longHasMass` or `floatHasMass` value) for simplicity sake. Feel free to create them in your project.

By using a group of generic functions that do not tie to a specific type, we can write more generic code.



### Cats

Cats is a library that contains typeclasses and standard typeclass instances for them, so we no longer need to write our own type class instances for a lot of the standard types like `Int`, `Double`, `String`, etc.
Libraries that build on cats share a common group of typeclasses, so tend to work well with each other.

We're going to add cats typeclasses into our code.


##### Structure 

Speaking from experience, I find it useful to have this high-level structure in mind when learning a new type class. If possible, I'd also write some simple codes to use it and test its operation.
```
Name of typeclass: _
Operation(s) defined by the typeclass: _
Function(s) to carry out the operation: _
Some common type instances of the typeclass: _ 
```
    
```
How to use it: _
```

#### Semigroup and Monoid

The typeclasses we'll explore are Semigroup and Monoid.

```scala
trait Semigroup[A] {
  def combine(a: A, b: A): A
}
```

```
Name of typeclass: Semigroup
operation defined by Semigroup: Combining two values with the same types.
Function to carry out the operation: combine
Some common type instances of Semigroup: Numeric types (Int, Double, Float, Long, etc.), String and List.
```

```scala
// How to use it:
val combinedInts: Int = Semigroup.combine(2, 3)  // 5
val combinedStrings: String = Semigroup.combine("Advent ", "of Code")  //  "Advent of Code" 
val combinedListOfInts: List[Int] = Semigroup.combine(List(1, 2), List(3, 4)) // List(1, 2, 3, 4)
val combinedListOfStrings: List[String] = Semigroup.combine(List("Advent of Code"), List("with Cats"))  //  List(Advent of Code, with Cats)
```
As you can see, the implementation of `combine` function varies according to the types. It is addition for `Int`, string concatenation for `String` and List concatenation for `List`. Nonetheless, fundamentally, they are doing the same thing: an associative operation which combines two values.  
  

[Semigroups](https://github.com/typelevel/cats/blob/master/kernel/src/main/scala/cats/kernel/Semigroup.scala) are useful when we want to combine things together, but don't want to tie ourselves down to what those things are.

For example, consider the following function:

```scala
def combineEverything[A](as: List[A], initial: A)(implicit S: Semigroup[A]): A = ???
```

Try implementing this function using `as.foldLeft`.  

We can use this function to combine anything that is an instance of a semigroup.

```scala
combineEverything(List(1,2, 3), 0)
combineEverything(List("foo", "bar"), "baz")
```

would be valid uses.

Here is our solution:
```scala
def combineEverything[A](as: List[A], initial: A)(implicit S: Semigroup[A]): A = as.foldLeft(initial)(S.combine)
```

##### Back to spacecraft:
Let's try using a semigroup and `foldLeft` to sum all our numbers in `sumFuels` function which has been parameterized by type .  Can you give it a try?

```scala
def sumFuels[A](fuels: List[A])(implicit S: Semigroup[A]): A = ???
```

You may have realised that you can't because we don't have quite enough information to sum all our fuels -specifically, we don't know what to do if our list of fuels is empty.

This is where `Monoid` come in.



#### Monoid:
The [Monoid](https://github.com/typelevel/cats/blob/master/kernel/src/main/scala/cats/kernel/Monoid.scala) typeclass has the Semigroup's `combine` function and an `empty` value.

```scala
trait Monoid[A] extends Semigroup[A] {
  def empty: A
}
```
```
Name of typeclass: Monoid
Operations: A semigroup with identity element. 
Functions: combine, empty
Type instances: Numeric types (Int, Double, etc.), String and List.
```

The `empty` value must be an identity element for the `combine` operation, that is to say

`combine(a, empty) = combine(empty, a) = a`

One such `empty` value is `0` on `+` operation of numbers. For example, `combine(2, 0) = combine(0, 2) = 2`  

Can you think of a valid `empty` value for each `combine` operations below?
 - `+` on `String`
 - `++` on `List[String]`
 
Which ones did you come up with?  Here are ours:
- value `""` (*empty string*) for `+` on `String` such that `combine("Cats", "") = combine("", "Cats") = "Cats"`
- value `List("")` for `++` on `List[String]` such that `combine(List("Advent of code"), List("")) = combine(List(""), List("Advent of code")) = List("Advent of code")`

You could try what we just said by writing some codes:

```scala
// How to use it:
val combinedWithEmptyInt: Int = Monoid.combine(2, Monoid.empty[Int])  // 2
val combinedWithEmptyStrings = Monoid.combine("Advent ", Monoid.empty[String])  // "Advent"
val combinedWithEmptyListOfStrings = Monoid.combine(Monoid.empty[List[String]], List("Advent of Code"))  //  List("Advent of Code")
```

If you take a look into the cats-kernel-instances library, you can see exactly how [Int](https://github.com/typelevel/cats/blob/master/kernel/src/main/scala/cats/kernel/instances/IntInstances.scala), [String](https://github.com/typelevel/cats/blob/master/kernel/src/main/scala/cats/kernel/instances/StringInstances.scala) and [List](https://github.com/typelevel/cats/blob/master/kernel/src/main/scala/cats/kernel/instances/ListInstances.scala) are implemented as instances of Monoid typeclass. 


##### Back to spacecraft:
Now, let's use Monoid to sum all numbers in the parameterized `sumFuels[A]` function. 

```scala
def sumFuels[A](fuels: List[A])(implicit M: Monoid[A]) = ???
```

How did your solution look like? Here is ours:

```scala
def sumFuels[A](fuels: List[A])(implicit M: Monoid[A]) = fuels.foldLeft(M.empty)(M.combine)
```

So far, we have parameterized and re-written `fuel` and `sumFuels` using typeclasses. To solve Day1's Part 1 problem, we need to re-write `calculateFuels` and `sumOfFuel` because they have been parameterized by type too. Can you give it a try?

```scala
def calculateFuels[A](masses: List[A])(implicit M: Mass[A]): List[A] = ???

def sumOfFuel[A](masses: List[A])(implicit M: Mass[A], N: Monoid[A]): A = ???
``` 


Did you try it? Here are our solutions:

```scala
def calculateFuels[A](masses: List[A])(implicit M: Mass[A]): List[A] = masses.map(fuel[A])

def sumOfFuel[A](masses: List[A])(implicit M: Mass[A], N: Monoid[A]): A = sumFuels(calculateFuels(masses))
```      

The codes of this section can be found in [day-1-step-3](https://github.com/lsug/advent-of-code-typelevel/blob/day-1-step-3/src/main/scala/advent/solutions/Day1.scala) branch.


## Day1 - Part 2

Let's move on to solving Part 2 of Day1. Can you give it a try?

How did you solve it? Here is here our solution:

```scala
def totalFuel(mass: Int): Int = {
      @tailrec
      def go(currentFuel: Int, accum: Int): Int =
        if (currentFuel <= 0) accum
        else go(Part1.fuel(currentFuel), accum + currentFuel)

      go(Part1.fuel(mass), 0)
    }
```
```scala
 def sumOfTotalFuel(masses: List[Int]): Int = masses.map(totalFuel).sum
```

We use `totalFuel` to find the total fuel required to launch a module by repeatedly applying `fuel[Int]` defined in Part1 to last calculated fuel value and add the calculated fuel value to the total, `accum` until the fuel value is less than zero.

We use a tail recursive function `go` in `totalFuel` because Scala automatically compiles the recursion to iterative loops that don’t consume call stack frames for each iteration.      

We then use `sumOfTotalFuel`, which applies `totalFuel` to a list of modules in the spacecraft and sums up all the total fuels, to get the final answer. 

The codes of this section can be found in [day-1-step-4](https://github.com/lsug/advent-of-code-typelevel/blob/day-1-step-4/src/main/scala/advent/solutions/Day1.scala) branch.


#### Order typeclass
The next typeclass we'll explore is [Order](https://github.com/typelevel/cats/blob/master/kernel/src/main/scala/cats/kernel/Order.scala) in Cats.

```scala
trait Order[A] {
  def compare(a: A, b: A): Int
}
```

```
Name of typeclass: Order
operation: comparing and ordering two values.
Functions: compare
Type instances: Numeric types (Int, Double, etc.), String and List.
```

```scala
// How to use it:
val smaller: Int = Order.compare(2, 3)  // -1
val larger: Int = Order.compare(3, 2)   //  1
val equalValue: Int = Order.compare(3, 3)  // 0
```

The `compare` function will return an `Int` whose sign is 
- negative iff `a < b`  
- positive iff `a > b`    
- zero iff `a = b`  

As an example, can you try to implement `isXLessThanY` function such that it returns `true` if `x` is less than or equal to `y`, else `false`.
```scala
def isXLessThanY[A](x: A, y: A)(implicit O: Order[A]): Boolean = ???
```
Here is our solution:
```scala
def isXLessThanY[A](x: A, y: A)(implicit O: Order[A]): Boolean = O.compare(x, y) <= 0
```

We can make the solution easier to read by using the infix notation `<=` defined in Cats. 
 
```scala
def isXLessThanY[A](x: A, y: A)(implicit O: Order[A]): Boolean = x <= y
``` 


##### Back to spacecraft:
Again, `totalFuel` and `sumOfTotalFuel` have been parameterized by a type in Part 2. Can you try implement them using `Mass`, `Monoid` and `Order` typeclasses?

```scala
def totalFuel[A](mass: A)(implicit M: Monoid[A], N: Mass[A], O: Order[A]): A = ???
def sumOfTotalFuel[A](masses: List[A])(implicit M: Monoid[A], N: Mass[A], O: Order[A]): A = ???
```

What are your solutions? Here are ours:

```scala
def totalFuel[A](mass: A)(implicit M: Monoid[A], N: Mass[A], O: Order[A]): A = {
      @tailrec
      def go(currentFuel: A, accum: A): A =
        if (currentFuel <= M.empty) accum
        else go(Part1.fuel(currentFuel), accum combine currentFuel)

      go(Part1.fuel(mass), M.empty)
    }
```
```scala
 def sumOfTotalFuel[A](masses: List[A])(implicit M: Monoid[A], N: Mass[A], O: Order[A]): A = {
      masses.map(totalFuel[A]).fold(M.empty)(M.combine)
    }
```

The codes of this section can be found in [day-1-step-5](https://github.com/lsug/advent-of-code-typelevel/blob/day-1-step-5/src/main/scala/advent/solutions/Day1.scala) branch.


#### Functor typeclass

In this section, we will explore [Functor](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Functor.scala) typeclass.
 
```scala
trait Functor[F[_]] {
  def map[A, B](fa: F[A])(f: A => B): F[B]
}
```

```
Name of typeclass: Functor
Operation: Mapping over type constructor.
Functions: map
Type instances: List, Vector, Option, Tuple, Either etc.
``` 
  
The `map` function in Functor typeclass is the same as the `map` method in Scala standard library. Therefore, any class that has a `map` method is an instance of Functor.

##### Type constructor
 
These Functor instances are also known as type constructors because they take one type as the parameter to produce a new type. 

For example, you provide a type of `Int` to `List` to create a value of concrete type `List[Int]`. The same thing with `Option`. 
 
```scala
val numbers: List[Int] = List(1, 2, 3, 4, 5, 6)
val one: Option[Int] = Some(1)
val name: Option[String] = Some("Cats")
```

`one` is a value of concrete type `Option[Int]` and `name` is a value of concrete type `Option[String]`. 

You cannot create a value with just type of `List` or `Option` because they are not a type per se but a type constructor. 

In the Functor signature above, you'll notice that, instead of a normal letter `A` as type parameter, `F[_]` is used. In Scala, this is the syntax for a type constructor parameter.   

It may look weird when you first encounter it, it actually is a very descriptive symbol for a type constructor parameter. Remember we said earlier a type constructor needs to take in a type, could be any type, to construct a value. Doesn't the shape of `[_]` look like a hole waiting to be filled in with a type?


##### Back to spacecraft:
Now, let's apply this to `calculateFuels` function which has been parameterized by `F[_]` and `A`. Can you try to re-write `calculateFuels[A]` using Functor?

```scala
private def calculateFuels[F[_], A](masses: F[A])(implicit M: Mass[A], G: Functor[F]): F[A] = ???
```  

Did you manage to solve it? Here is our solution.

```scala
private def calculateFuels[F[_], A](masses: F[A])(implicit M: Mass[A], G: Functor[F]): F[A] = masses.map(fuel[A])
```  

##### F[_]  vs F[A]
However subtle there is a significant difference between `F[_]` and `F[A]`. `F[A]` is a type, a concrete type even though we don't know what the type is now as it depends on what type we eventually replace it with. `F[A]` could be `List[Int]`, `Vector[Double]` or `Option[Int]`, etc.

Just as `A` is a symbol for type parameter, `F[_]` is a symbol for type constructor parameter. Thus, it is not a concrete type. Some examples of `F[_]` are `List`, `Vector` and `Option`. Notice, unlike previous examples, there are no `[Int]` and `[Double]` because `F[_]`, the whole thing means a set of type constructors which includes `List`, `Vector`, `Option` and many other type constructors that supports the `map` operation of Functor typeclass.
           
This is best illustrated by creating a Functor instance for `List`. Let's name it `listHasFunctor`.

```scala
  val listHasFunctor: Functor[List] = new Functor[List] {
    override def map[A, B](fa: List[A])(f: A => B): List[B] = fa.map(f)
  }
``` 

Notice that only `List` is used at the place of `F[_]`. This is because Functor only concerns itself with type constructor and does not care what type we put into the type constructor. 


#### Foldable typeclass

```scala
trait Foldable[F[_]] {
  def fold[A](fa: F[A])(f: (A, A) => A): A
}
```

```
Name of typeclass: Foldable
Operation: Folding a data structure to a summary value.
Functions: fold, foldLeft, foldRight
Type instances: List, Vector, Option, etc.
``` 

The `fold` function in [Foldable](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Foldable.scala) typeclass is the same as the `fold` method in Scala Standard library. Therefore, any class that has `fold` method is an instance of Foldable.  

##### Back to spacecraft:
Now, let's apply this to our `sumOfTotalFuel` function which has been parameterized by `F[_]` and `A`. Can you try to re-write `sumOfTotalFuel[A]` using Functor and Foldable?

```scala
def sumOfTotalFuel[F[_], A](masses: F[A])(implicit N: Mass[A], O: Order[A], G: Functor[F], H: Foldable[F]): F[A] = ???
```  

Hopefully you manage to solve it. Here is our solution:

```scala
def sumOfTotalFuel[F[_], A](masses: F[A])(implicit N: Mass[A], O: Order[A], G: Functor[F], H: Foldable[F]): F[A] = masses.map(totalFuel[A]).fold
``` 

The codes of this section can be found in [day-1-step-6](https://github.com/lsug/advent-of-code-typelevel/blob/day-1-step-6/src/main/scala/advent/solutions/Day1.scala) branch.