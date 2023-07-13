# SQL2MSFOL
SQL2MSFOL is an implementation of mapping from SQL statements to Many-Sorted First-Order Logic (MSFOL). This Java implementation based on the mapping definition described in the manuscript [Proving correctness for SQL implementations of OCL constraints]() submitted to MoDELS conference 2022.

In a nutshell, given a datamodel, SQL2MSFOL maps an SQL statement into a complete FOL formulae written in [SMT-LIB2](https://smtlib.cs.uiowa.edu/papers/smt-lib-reference-v2.6-r2021-05-12.pdf) language.

## Supported language and features:

Although SQL2MSFOL covers a large subset of SQL statements and expressions, 
the implementation is an on-going project. 
The following items highlight the supported subset of SQL select statement:

```
SELECT selectitems
SELECT selectitems FROM fromitem
SELECT selectitems FROM fromitem WHERE whereExpr
SELECT selectitems FROM fromitem JOIN fromitem
SELECT selectitems FROM fromitem JOIN fromitem ON onExpr
SELECT selectitems FROM fromitem JOIN fromitem WHERE whereExpr
SELECT selectitems FROM fromitem JOIN fromitem ON onExpr WHERE whereExpr
```

where

```
selectitems := expr (, expr)*
fromitem    := class 
             | association-class 
             | subselect
whereExpr   := boolexpr
onExpr      := boolexpr
```

and

```
expr := TRUE | FALSE | NULL    (boolean literals)
      | ... | -1 | 0 | 1 | ... (integer literals)
      | var                    (variables)
      | 'a string'             (string literals)
      | id                     (class-id)
      | attribute              (class-attributes)
      | association-end        (association-ends)
      | NOT boolexpr           (unary operations)
      | boolexpr AND boolexpr  (binary operations, i.e., AND, OR)
      | expr = expr            (binary comparisons, i.e., =, <>, >, <, >=, <=)
      | expr IS NULL           (IS NULL expression)
      | CASE WHEN boolexpr THEN expr ELSE expr END
                               (CASE expression)
      | EXISTS subselect       (EXISTS expression)
```

## How to use

### Requirements:
- (required) `Maven 3` and `Java 1.8` (or higher).

### Quick guideline:
Users can either clone this repository directly 

```
git clone https://github.com/npbhoang/SQL2MSFOL.git
```

or pull it as package from the Maven Central.

```
<dependency>
  <groupId>io.github.modelsvgu</groupId>
  <artifactId>SQL2MSFOL</artifactId>
  <version>1.0.0</version>
</dependency>
```

### For usage
Have a quick look at the `Runner.java` [class](https://github.com/npbhoang/SQL2MSFOL/blob/Clean/src/main/java/Runner.java) for a quick guideline.

You can invoke it as a standalone application by the following command:

```bash
java -jar sql2msfol-1.0.0.jar
  -in <input_datamodel_url>
  -ctx [<var>:<type>]*
  -sql <sql_stm>
```

