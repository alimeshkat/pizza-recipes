# Application

The main method in the application class represents the entry point of our application. After converting it to
Kotlin, you will notice some small changes. For example, the `main()` function can be defined outside the
with `@application`
annotated class (functions are first class citizens). So, let's get into it.

## Convert RecipesApplication

1) Convert
   the [RecipesApplication class](../../recipe-java/src/main/java/nl/rabobank/kotlinmovement/recipes/RecipesApplication.java)
   .
2) To make it more Kotlin like, let's move the `main` function to the outside of the class and place it on the same
   level as the `class`. The generated `@JvmStatic` can be removed now.
3) Replace `SpringApplication.run(RecipesApplication::class.java, *args)` by the Springs own Kotlin extension
   function `runApplication<RecipesApplication>(*args)`
4) And change class type of the `RecipesApplication` which was `object` to `class` *otherwise it won't be able to do
   Spring stuff!!*
5) When ready, run all tests:

```shell
   (cd ../.. && ./mvnw clean verify)
   ```

--- 
![light-bulb](../sources/png/light-bulb-xs.png)  
Note that the `runApplication()` doesn't need the application class type as an argument, it uses the type parameter of the function.
In Java that's not possible because of the type erasure at runtime. But in Kotlin it is possible to use the type parameter in a function 
when the function is an [inline function](https://kotlinlang.org/docs/inline-functions.html#reified-type-parameters).
---

[Go to next section](../5-controller/Recipe.md)