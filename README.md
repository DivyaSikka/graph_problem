# graph_problem
Solving a problem using graph theory

Here is the description of the problem:

ﾳmer John's hobby of conducting high-energy physics experiments on weekends has backfired, causing N wormholes (2 <= N <= 12, N even) to materialize on his farm, each located at a distinct point on the 2D map of his farm (the x,y coordinates are both integers).

According to his calculations, Farmer John knows that his wormholes will form N/2 connected pairs. For example, if wormholes A and B are connected as a pair, then any object entering wormhole A will exit wormhole B moving in the same direction, and any object entering wormhole B will similarly exit from wormhole A moving in the same direction. This can have rather unpleasant consequences.

For example, suppose there are two paired wormholes A at (1,1) and B at (3,1), and that Bessie the cow starts from position (2,1) moving in the +x direction. Bessie will enter wormhole B [at (3,1)], exit from A [at (1,1)], then enter B again, and so on, getting trapped in an infinite cycle!

   | . . . .

   | A > B .      Bessie will travel to B then

   + . . . .      A then across to B again

Farmer John knows the exact location of each wormhole on his farm. He knows that Bessie the cow always walks in the +x direction, although he does not remember where Bessie is currently located except that she is not on top of a wormhole.

Please help Farmer John count the number of distinct pairings of the wormholes such that Bessie could possibly get trapped in an infinite cycle if she starts from an unlucky position. FJ doesn't know which wormhole pairs with any other wormhole, so \fBfind all the possibilities (i.e., all the different ways that N wormholes could be paired such that Bessie can, in some way, get in a cycle\fP). Note that a loop with a smaller number of wormholes might contribute a number of different sets of pairings to the total count as those wormholes that are not in the loop are paired in many different ways.

