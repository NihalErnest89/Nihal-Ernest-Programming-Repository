/*********************************************************************************
* Nihal Ernest, nernest
* 2022 Winter CSE101 PA7
* Order.cpp
* Reads keys from an input file and adds them to a Dictionary
*********************************************************************************/
#include <iostream>
#include <fstream>
#include <stdexcept>
#include <string>

#include "Dictionary.h"

int main(int argc, char *argv[]) {
    std::ifstream in;
    std::ofstream out;

    // checks if number of command line arguments are valid
    if (argc != 3) {
        std::cerr << "Usage: " << argv[0] << " <input file> <output file>" << std::endl;
        return(EXIT_FAILURE);
    }

    // checks if input file is valid
    in.open(argv[1]);
    if (!in.is_open()) {
        std::cerr << "Unable to open file " << argv[1] << " for reading" << std::endl;
        return(EXIT_FAILURE);
    }

    // checks if output file is valid
    out.open(argv[2]);
    if (!out.is_open()) {
        std::cerr << "Unable to open file " << argv[2] << " for writing" << std::endl;
        return(EXIT_FAILURE);
    }

    std::string line;
    Dictionary A;
    int count = 1;

    // reads in each line and adds the string to the dictionary as a key with the line number as value
    while(getline(in, line)) {
        A.setValue(line, count);
        count += 1;
    }
    
    // prints in order string representation of dictionary
    out << A << std::endl;

    // prints pre order string representation of dictionary
    out << A.pre_string() << std::endl;

    return 0;
}