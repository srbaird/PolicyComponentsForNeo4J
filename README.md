## Policy Database on [Neo4j](https://neo4j.com)

This a section of an existing application which used Neo4j as data persistence which broadly mirrors the functionality of the Google Datastore [example](https://github.com/srbaird/PolicyComponentsForDataStore). A [document]() exists to give background into the approach
___

In short the project creates a method of converting a graph data store into macro entities based on a composite pattern. Thus a graph of entities and relationships starting at the centre node...

<p align="center">
<img src="https://github.com/srbaird/PolicyComponentsForNeo4J/blob/master/docs/graph.jpg" alt="Graph Example"  >
</p>

...would be converted into a composite structure as follows.

<p align="center">
<img src="https://github.com/srbaird/PolicyComponentsForNeo4J/blob/master/docs/structure.jpg" alt="Structure Example"  >
</p>

___

While this implementation is broadly similar to the other [example](https://github.com/srbaird/PolicyComponentsForDataStore) it implements an extra feature which allows select predicates to be applied to the composite structure. Both basic structures [Entity](https://github.com/srbaird/PolicyComponentsForNeo4J/blob/master/src/main/java/com/bac/components/Entity.java) and [Relationship](https://github.com/srbaird/PolicyComponentsForNeo4J/blob/master/src/main/java/com/bac/components/Relationship.java) implement default behaviour encapsulated in [ContextAware](https://github.com/srbaird/PolicyComponentsForNeo4J/blob/master/src/main/java/com/bac/components/ContextAware.java). This allows any subclass of these basic structures to respond to a [Context](https://github.com/srbaird/PolicyComponentsForNeo4J/blob/master/src/main/java/com/bac/components/Context.java) which determines whether that component is included in the results of a predicate.
The [Dispatcher](https://github.com/srbaird/PolicyComponentsForNeo4J/blob/master/src/main/java/com/bac/application/impl/LateBindingContextDispatcher.groovy) is a Groovy class which allows it to overcome the Java's static binding on overloaded methods. This means that the appropriate method for each Context will be derived at runtime. Any class implementing ContextAware may implement a method which will be discovered without having to be registered. For example

```
class SomeEntity extends Entity  {
...
	boolean accept(SomeDateContext context) {

		return context.isFieldValid(someLocalField);
	}
...
}
```

For the above example the following will determine whether the instance is valid for the context

```
	SomeEntity entity;
	SomeDateContext context;
	LateBindingContextDispatcher dispatcher;
	
	boolean isValidForContext = dispatcher.dispatch(entity, context);
	
```

This dispatching behaviour is combined with a [predicate](https://github.com/srbaird/PolicyComponentsForNeo4J/blob/master/src/main/java/com/bac/application/predicate/Where.java) builder which allows complex conditional views of a data structure to be obtained.

```
import static com.bac.application.predicate.Where.where;
import static com.bac.application.predicate.Where.and;
import static com.bac.application.predicate.Where.or;

	ContextAware element;

	boolean isValid = where(dateContext, and(typeContext, or(privilegeContext))).apply(element);

```

In this case the element that satisfies the date context AND either the type context OR the privilege context will return true for this query.

___



If building this project using Eclipse it may be necessary to enable Groovy. The following is an example to achiive this.

```
Help → Install New Software…

	Work with: http://dist.springsource.org/snapshot/GRECLIPSE/e4.6/
	Install all options.

Right click on project → Configure → Convert to Groovy Project
Right click on project → Properties → Groovy Compiler → select appropriate compiler level (e.g. 2.4)
Update pom.xml to ensure that the Groovy dependency version matches the compiler level (e.g. 2.4.8)
```


