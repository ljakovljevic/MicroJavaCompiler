A full compiler implementation for a MicroJava-like programming language, developed as part of a compiler construction project.

## Overview

This project implements a complete compilation pipeline:

- Lexical analysis (JFlex)

- Syntax analysis (CUP)

- Abstract Syntax Tree (AST)

- Semantic analysis (type checking, symbol table)

- Bytecode generation for a stack-based virtual machine

The compiler supports a range of language features including control flow, functions, arrays, and enums.

---

## Technologies

- Java

- JFlex (Lexer)

- CUP (Parser generator)

- Custom AST traversal

- Stack-based Virtual Machine (MJ runtime)

---

## Supported Language Features

- Primitive types (`int`, `char`, `bool`)

- Variables and arrays

- Functions and parameters

- Arithmetic and logical expressions

- Control flow:

  - `if / else`

  - `for` loops (with `break` and `continue`)

  - `switch-case` with fall-through

  - Ternary operator (`?:`)

- Short-circuit evaluation (`&&`, `||`)

- Enums with constant values

- Built-in functions (`print`, `read`, `ord`, etc.)

---

## Compilation Pipeline

1. **Lexical Analysis**

   - Tokenization using JFlex

2. **Syntax Analysis**

   - Grammar defined in CUP

   - AST construction

3. **Semantic Analysis**

   - Symbol table management

   - Type checking

   - Scope resolution

   - Validation of expressions and statements

4. **Code Generation**

   - Bytecode generation for a stack-based VM

   - Control flow handling using jump fixups

   - Function call support and stack management

---

## 📌 Notable Implementation Details

- Implemented **short-circuit evaluation** for logical operators

- Designed **switch-case dispatch logic** with proper fall-through handling

- Managed **nested loops with break/continue fixup stacks**

- Implemented **ternary operator code generation**

- Handled **array access and memory model via stack operations**

- Debugged runtime execution using disassembly and VM tracing

---

## Author

Lazar Jakovljevic
