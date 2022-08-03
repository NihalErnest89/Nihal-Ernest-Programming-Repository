/*********************************************************************************
* Nihal Ernest, nernest
* 2022 Winter CSE101 PA7
* Dictionary.cpp
* Dictionary ADT
*********************************************************************************/
#include <iostream>
#include <string>
#include "Dictionary.h"
#define NIL -1

Dictionary::Node::Node(keyType k, valType v) {
    key = k;
    val = v;
    parent = nullptr;
    left = nullptr;
    right = nullptr;
}

Dictionary::Dictionary() {
    nil = new Node("\000", NIL);
    root = nil;
    root->parent = nil;
    root->left = nil;
    root->right = nil;
    current = nil;
    current->left = nil;
    current->right = nil;
    current->parent = nil;
    num_pairs = 0;
}

Dictionary::Dictionary(const Dictionary& D) {
    nil = new Node("\000", NIL);
    root = nil;
    preOrderCopy(D.root, D.nil);
    current = nil;
    num_pairs = D.num_pairs;
}

Dictionary::~Dictionary() {
    clear();
    delete nil;
}

int Dictionary::size() const {
    return num_pairs;
}

void Dictionary::inOrderString(std::string& s, Node* R) const {
    if (R != nil) {
        inOrderString(s, R->left);
        s += R->key + " : " + std::to_string(R->val) + "\n";
        inOrderString(s, R->right);
    }
    return;
}

void Dictionary::preOrderString(std::string& s, Node* R) const {
    if (R != nil) {
        s += R->key + "\n";
        preOrderString(s, R->left);
        preOrderString(s, R->right);
    }
    return;
}

void Dictionary::preOrderCopy(Node* R, Node* N) {
    if (R != N) {
        setValue(R->key, R->val);
        preOrderCopy(R->left, N);
        preOrderCopy(R->right, N);
    }
}

Dictionary::Node* Dictionary::search(Node* R, keyType k) const {
    if (R == nil || k == R->key) {
        return R;
    }
    else if (k < R->key) { // change this to k.compare(R.key) if it causes issues
        return search(R->left, k);
    }
    else {
        return search(R->right, k);
    }
}

Dictionary::Node* Dictionary::findMin(Node* R) {
    while (R->left != nil) {
        R = R->left;
    }
    return R;
}

Dictionary::Node* Dictionary::findMax(Node* R) {
    while (R->right != nil) {
        R = R->right;
    }
    return R;
}

Dictionary::Node* Dictionary::findNext(Node* R) {
    if (R->right != nil) {
        return findMin(R->right);
    }
    Node* Y = R->parent;
    while (Y != nil && R == Y->right) {
        R = Y;
        Y = Y->parent;
    }
    return Y;
}

Dictionary::Node* Dictionary::findPrev(Node* R) {
    if (R->left != nil) {
        return findMax(R->left);
    }
    Node* Y = R->parent;
    while (Y != nil && R == Y->left) {
        R = Y;
        Y = Y->parent;
    }
    return Y;
}

bool Dictionary::contains(keyType k) const {
    if (search(root, k) != nil) {
        return true;
    }
    return false;
}

valType& Dictionary::getValue(keyType k) const {
    Node* temp = search(root, k);
    if (temp == nil) {
        throw std::logic_error("Dictionary: getValue(): key \"" + k + "\" does not exist");
    }
    return temp->val;
}

bool Dictionary::hasCurrent() const {
    return current != nil;
}

keyType Dictionary::currentKey() const {
    if (hasCurrent() == false) {
        throw std::logic_error("Dictionary: currentKey(): current undefined");
    }
    return current->key;
}

valType& Dictionary::currentVal() const {
    if (hasCurrent() == false) {
        throw std::logic_error("Dictionary: currentVal(): current undefined");
    }
    return current->val;
}

void Dictionary::postOrderDelete(Node* R) {
    if (R != nil) {
        postOrderDelete(R->left);
        postOrderDelete(R->right);
        delete R;
    }
    num_pairs = 0;
    current = nil;
    return;
}

void Dictionary::clear() {
    postOrderDelete(root);
    root = nil;
    return;
}

void Dictionary::setValue(keyType k, valType v) {
    // Node* temp = search(roots, k);
    // if (temp != nil) {
    //     temp->val = v;
    //     return;
    // }
    num_pairs += 1;
    Node* y = nil;
    Node* x = root;
    while (x != nil) { 
        if (x->key == k) {
            x->val = v;
            num_pairs -= 1;
            return;
        }
        y = x;
        if (k < x->key) {
            x = x->left;
        }
        else {
            x = x->right;
        }
    }
    Node* z = new Node(k, v);
    z->parent = y;
    if (y->key == nil->key) {
        root = z;
        root->left = nil;
        root->right = nil;
    }
    else if (k < y->key) {
        y->left = z;
        z->left = nil;
        z->right = nil;
    }
    else {
        y->right = z;
        z->left = nil;
        z->right = nil;
    }
    
    return;
}

void Dictionary::transplant(Node* u, Node* v) {
    if (u->parent == nil) {
        root = v;
    }
    else if (u == u->parent->left) {
        u->parent->left = v;
    }
    else {
        u->parent->right = v;
    }
    if (v != nil) {
        v->parent = u->parent;
    }
    return;
}

void Dictionary::remove(keyType k) {
    if (contains(k) == false) {
        return;
    }

    num_pairs -= 1;
    Node* z = search(root, k);
    
    if (z->left == nil) {
        transplant(z, z->right);
    }
    else if (z->right == nil) {
        transplant(z, z->left);
    }
    else {
        Node* y = findMin(z->right);
        if (y->parent != z) {
            transplant(y, y->right);
            y->right = z->right;
            y->right->parent = y;
        }
        transplant(z, y);
        y->left = z->left;
        y->left->parent = y;
        
    }
    if (current == z) {
        current = nil;
    }
    delete z;
    return;
}

void Dictionary::begin() {
    if (root == nil) {
        return;
    }
    current = findMin(root);
    return;
}

void Dictionary::end() {
    if (root == nil) {
        return;
    }
    current = findMax(root);
    return;
}

void Dictionary::next() {
    current = findNext(current);
    return;
}

void Dictionary::prev() {
    current = findPrev(current);
    return;
}

std::string Dictionary::to_string() const {
    std::string s = "";
    inOrderString(s, root);
    return s;
}

std::string Dictionary::pre_string() const {
    std::string s = "";
    preOrderString(s, root);
    return s;
}

bool Dictionary::equals(const Dictionary& D) const {
    std::string a, b;
    inOrderString(a, root);
    inOrderString(b, D.root);
    return (a == b);
}

std::ostream& operator<<( std::ostream& stream, Dictionary& D ) {
    return stream << D.Dictionary::to_string();
}

bool operator==( const Dictionary& A, const Dictionary& B ) {
    return A.Dictionary::equals(B);
}

Dictionary& Dictionary::operator=( const Dictionary& D ) {
    if (this != &D) {
        Dictionary temp = D;

        std::swap(root, temp.root);
        std::swap(current, temp.current);
        std::swap(num_pairs, temp.num_pairs);
        std::swap(nil, temp.nil);
    }
    
    return *this;
}
