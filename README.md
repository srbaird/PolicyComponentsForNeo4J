## Policy Database on [Neo4j](https://neo4j.com)

This a section of an existing application which used Neo4j as data persistence which broadly mirrors the functionality of the Google Datastore [example](https://github.com/srbaird/PolicyComponentsForDataStore). A [document]() exists to give background into the approach
___

If building this project using Eclipse it may be necessary to enable Groovy. The following is an example to acheive this.

```
Help → Install New Software…

	Work with: http://dist.springsource.org/snapshot/GRECLIPSE/e4.6/
	Install all options.

Right click on project → Configure → Convert to Groovy Project
Right click on project → Properties → Groovy Compiler → select appropriate compiler level (e.g. 2.4)
Update pom.xml to ensure that the Groovy dependency version matches the compiler level (e.g. 2.4.8)
```


