# Propositional-Formula-Parser
A parser that determines whether a user-entered string is a well-formed propositional formula (wff); this parser also displays the construction of a wff as a binary tree, and also computes truth values.

A wff is built using propositional variables and formation rules that govern how those variables can be put together.

A propositional variable (or simply variable) can be any letter followed by one or more apostrophes; for example: p, Q, r', s''' are variables.

The formation rules for wffs:

(1)Variables are wffs.

(2) If x, y are wffs, then ~x, (x=>y), (x&y), (x||y), (x<=>y) are wffs. (Note that these are the usual connectives: negation, implication, conjunction, disjuntion, and the biconditional.)

(3) Something is a wff only if it is constructed by applying rules 1 and 2 a finite number of times.

The truth value of a user-entered wff is computed using user-entered truth values for the variables in the wff, and according to the usual truth functions associated with each connective.

For an in-depth look at the theoretical foundation behind the parser, see the theoretical_foundation.pdf file.
