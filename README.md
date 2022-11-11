Weighted A* and Derivative Heuristic path planning algorithma are implemented. 
for doing test: 
1- copy the content of test case to the test_file.txt 
2- run the code

note: you can change the value of "l" and "alpha" for each test case in the code.
note2: for runnig Weighted A* do:
        1- comment this line => Gui.path = as.WA_DH(Gui.start, Gui.target);
        2- uncomment this line => Gui.path = as.WA_star(Gui.start, Gui.target);
        
note2: for runnig Derivative Heuristic do:
        1- comment this line => Gui.path = as.WA_star(Gui.start, Gui.target);
        2- uncomment this line => Gui.path = as.WA_DH(Gui.start, Gui.target);
