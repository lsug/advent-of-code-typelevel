
# Advent of Code with Cats

## Day1 - Part 1

### Functional Solution

Even though the first part of Day1 problem can be easily solved in one line of code, we purposely break it down into multiple small functions so that we can demonstrate how to apply typeclass to the problem later. We first write a fuel() function that takes in a mass of the module, divides by three and subtracts two to find the fuel required to launch a module of a given mass. In the problem, your puzzle input is a long list of all the modules on your spacecraft. To find the fuel required for each module, you could apply the fuel() function to each module of the long list by using map function.
 
This is what we’ve done by writing calculateFuels() function. Next, to sum up the list of fuels, we make use of the sum method available to Iterable like List in our case, and write a sumFuels() function. Finally, we put all of these small functions into sumOfFuel() function and solve our Part 1’s problem. 

The solution of Part 1 can be found in [day-1-step-1-abstraction](https://github.com/lsug/advent-of-code-typelevel/tree/day-1-step-1-abstraction) branch. 

### Type Abstraction

Before jumping into typeclass, we thought it’d be good to start by introducing generic function and type. Have a look at the three functions below.

```scala
def returnInt(x : Int): Int = x
def returnLong(x : Long): Long = x
def returnBoolean(x : Boolean): Boolean = x
```


`returnInt` is a function that accepts and returns a value of type `Int` whereas `returnLong` accepts and returns a type `Long` value and finally `returnBoolean`, a `Boolean` type of value.

Although they each return a different **type** of value, they share the same pattern: return the same value that was used as its argument. Since they do the same thing fundamentally, we could write a single function that does that. In other words, we could abstract over this pattern.

````scala
def identity[A](a: A): A = a
````

`identity[A]` function is such a function and is called a generic function. The square bracket with A letter in it is the type parameter, syntax of generic function in Scala. When we are generalizing, we tend to use letter A, B, C by convention.

Now, to return an `Int`, we can pass in an `Int` value to the identity function like this, `identity[Int](10)`. When you run this, the output will be a value of 10 which is the same as running `returnInt(10)`. By the same logic, to return a `true` value, we simply write `identity[Boolean](true)`.

Also, you could have as many generic types, also known as type parameters, in a function as you like. For example, there are three different types in `tuple3[A, B, C]` below

````scala
def tuple3[A, B, C](a: A, b: B, c: C): (A, B, C) = (a, b, c)
````

So, what is a type? You can think of a type as a set of values. For concrete types like `Boolean`, we know it is a set with just two values: true and false.(draw circle) Earlier, we mentioned that A is a generic type which means we don’t know anything about the possible values lie in set A. (draw circle)

You might be thinking why is having a generic type in a function a good thing? Let’s go back to the `identity[A]` function,  remember how it can take in and return an `Int`, `Long` and `Boolean`. Actually, it can do more than that, it can take in and return **any** type of values due to having a generic type. Compared it to `returnInt`, which can only take in and return a concrete `Int` value, `identity[A]` is a lot more flexible and powerful.

In short, by having a generic function, we can reduce code duplication and achieve polymorphism.

In [day-1-step-1-abstraction](https://github.com/lsug/advent-of-code-typelevel/tree/day-1-step-1-abstraction) branch, there is an exercise called Abstraction. Please try to make every function generic and see if you can pass all the tests.

### Type Class

We’ve seen both type and generic function, let’s go into typeclass. Before bombarding you with lots of definitions of what a typeclass is, we thought it would be good to give you a break and go back to the spacecraft. Remember the `fuel` function, let’s try to rewrite the function and make it generic.

If you do that, you’ll notice that the function does not compile and the error message is
 
````text
value / is not a member of type parameter A
````

What the error message is saying is the compiler cannot find `/`, division operation under type `A`. Previously, when the mass parameter was a concrete type `Int`, the function compiled because `/` is defined under type `Int`. Actually, it’s not only value `/` undefined, `-` (minus), `3` and `2` are also undefined under type A.

To make the generic `fuel[A]` function compile, we need to define all of them and we do that in an interface, `trait Mass[A]`.
