/*********************************************************************************
* Nihal Ernest, nernest
* 2022 Winter CSE101 PA7
* DictionaryTest.cpp
* A test client for the Dictionary ADT
*********************************************************************************/
#include<iostream>
#include<string>
#include<stdexcept>
#include"Dictionary.h"

using namespace std;

int main() {    
    Dictionary A;
    
    A.setValue("d", 1);
    A.setValue("b", 5);
    A.setValue("a", 10);
    A.setValue("c", 15);
    A.setValue("f", 20);
    A.setValue("e", 25);
    A.setValue("g", 30);
    A.begin();
    A.remove("a");

    if (A.hasCurrent())
        cout << "1" << endl;
    A.end();
    cout << "test" << endl;
    cout << A.currentKey() << endl;
    A.remove("d");
    if (A.currentKey() != "g" || A.currentVal() != 30)
        cout << "2" << endl;
    A.remove("g");
    if (A.hasCurrent())
        cout << "3" << endl;
    
    A.clear();
    if (A.size() > 0) {
        cout << "Size is bigger than it should be" << endl;
    }

    try {
        A.begin();
        A.currentVal();
        cout << "currentVal should throw an exception" << endl;
    }   
    catch (logic_error const &) {
    }

    Dictionary B = A;
    cout << "A = " << A << endl;
    cout << "B = " << B << endl;

    Dictionary C;
    C = B;

    cout << "B = " << B << endl;
    cout << "C = " << C << endl;

    A.clear();

    cout << "B = " << B << endl;
    cout << "C = " << C << endl;

    cout << "B (pre) = " << B.pre_string() << endl;
    cout << "C (pre) = " << C.pre_string() << endl;

    return (EXIT_SUCCESS);
}