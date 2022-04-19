#include<iostream>
#include<stdio.h>
#include<ctype.h>
#include<math.h>
using namespace std;

class calc {
	public: 
		double n[100] = { 0.0 },ans = 0.0;
		int dec_pow = -1;
		int i = 0;
		char opr[99] = { 0 };
		bool dec= false, fix = true;

		void clear() {
			n[100] = { 0.0 };
			i = 0;
			opr[99] = { 0 };
			ans = 0.0;
			dec = false;
			dec_pow = -1;
		}
};

//State 001 NOTHING/START 
void startINFIX(calc a, char key);
//State 002 BUILD NUMBER 
void buildNumber(calc a, char key);
void addDec(calc a, char key);
//State 003 INSERT OPR 
void insertOpr(calc a, char key);
//State 004 COMPUTE 
void compute(calc a, char key);
double apply(calc a);

//State 010 NOTHING 
void startPFIX(calc a, char key);
//State 020 BUILD NUMBER 
void buildNumberP(calc a, char key);
//State 030 INSERT OPR 
void insertOprP(calc a, char key);
//State 040 COMPUTE
void computeP(calc a, char key);


//SUPPORT FUNCTIONS 
int buttonReader(char key);
bool isOpr(char key);
int toInt(char key);

int main() {
	char key = 0;
	calc a;
	startINFIX(a, key);
	cin.ignore();
}

void startINFIX(calc a, char key) {
	int input = 0; 
	a.clear();
	a.fix = true;
	std::cout << "\n INFIX Calculator Has Been Cleared. Ready To Begin \n";
	std::cin >> key;

	input = buttonReader(key);
	if (input == 1)
		buildNumber(a, key);
	else if (input == 6)
		startPFIX(a,key);
	else
		startINFIX(a, key);
}
void startPFIX(calc a, char key) {
	int input = 0;
	a.fix = false;
	a.clear();
	std::cout << "\n PFIX Calculator Has Been Cleared. Ready To Begin \n";
	std::cin >> key;

	input = buttonReader(key);
	if (input == 1)
		buildNumberP(a, key);
	else if (input == 6)
		startINFIX(a,key);
	else
		startPFIX(a, key);
}


void buildNumber(calc a, char key) {
	if (a.dec == false) {
		if (a.n[a.i] == 0)
			a.n[a.i] = toInt(key);
		else
			a.n[a.i] = (a.n[a.i]* 10) + toInt(key);
	}
	else {
		a.n[a.i] = a.n[a.i] + (pow(10, a.dec_pow)*toInt(key));
		a.dec_pow--;
	}
	std::cout << '[' << a.i << "]  " << a.n[a.i];

	std::cout << "\n \n Continue to build number";
	std::cout << a.i;
	std::cout << " or type opr to continue \n";
	std::cout << "IN: ";
	cin >> key;
	int x = buttonReader(key);
	std::cout << "X " << x;
	if (x == 1) {
		buildNumber(a, key);
	}
	else if (x == 2) {
		insertOpr(a,key);
	}
	else if (x == 3) {
		compute(a, key);
	}
	else if (x == 4) {
		addDec(a, key);
	}
	else if (x == 6) {
		startPFIX(a, key);
	}
	else
		std::cout << "ERROR a button has been pressed that is not actualy on the calculator";
}
void addDec(calc a, char key) {
	a.dec = true;
	std::cout << '[' << a.i << "]  " << a.n[a.i] <<'.';
	std::cout << "\n \n Continue to build number";
	std::cout << a.i;
	std::cout << " or type opr to continue \n";
	std::cout << "IN: ";
	cin >> key;
	if (buttonReader(key) == 1) {
		buildNumber(a, key);
	}
	else
		addDec(a, key);
}

void buildNumberP(calc a, char key) {
	if (a.dec == false) {
		if (a.n[a.i] == 0)
			a.n[a.i] = toInt(key);
		else
			a.n[a.i] = (a.n[a.i] * 10) + toInt(key);
	}
	else {
		a.n[a.i] = a.n[a.i] + (pow(10, a.dec_pow)*toInt(key));
		a.dec_pow--;
	}
	std::cout << '[' << a.i << "]  " << a.n[a.i];

	std::cout << "\n \n Continue to build number";
	std::cout << a.i;
	std::cout << " or type opr to continue \n";
	std::cout << "IN: ";
	cin >> key;
	int x = buttonReader(key);
	std::cout << "X " << x;
	if (x == 1) {
		buildNumberP(a, key);
	}
	else if (x == 2) {
		insertOprP(a, key);
	}
	else if (x == 4) {
		addDec(a, key);
	}
	else if (x == 5) {
		a.i++;
		buildNumberP(a, key);;
	}
	else if (x == 6) {
		startINFIX(a, key);
	}
	else
		std::cout << "ERROR a button has been pressed that is not actualy on the calculator";
}

void insertOpr(calc a, char key) {
	a.opr[a.i] = key;
	std::cout << a.opr[a.i]  << '[' << a.i << "]  " << a.n[a.i];
	std::cout << "\n \n OPR ADDED to ";
	std::cout << a.i;
	std::cout << " or type opr to change \n";
	std::cout << "IN: ";
	cin >> key; 
	int x = buttonReader(key);
	if (x == 1) {
		a.i++; 
		a.dec_pow = -1;
		a.dec = false;
		buildNumber(a, key);
	}
	else if (x == 2) {
		insertOpr(a, key);
	}
	else if (x == 6) {
		//Switch
	}
	else
		std::cout << "ERROR a button has been pressed that is not actualy on the calculator";
}
void insertOprP(calc a, char key) {
	a.opr[a.i] = key;
	std::cout << a.opr[a.i] << '[' << a.i << "]  " << a.n[a.i];
	std::cout << "\n \n OPR ADDED to ";
	std::cout << a.i;
	
	computeP(a, key);
}


void compute(calc a, char key) {
	a.ans= apply(a);
	std::cout << "\n Result: ";
	std::cout << a.ans;
	std::cout << "\n";

	std::cout << "\n \n Continue to build number";
	std::cout << a.i;
	std::cout << " or type opr to continue \n";
	std::cout << "IN: ";
	cin >> key;
	int x = buttonReader(key);
	std::cout << "X " << x;
	if (x == 1) {
		a.i = 1;
		a.n[0] = a.ans;
		buildNumber(a, key);
	}
	else if (x == 6) {
		//Switch
	}
	else
		std::cout << "ERROR a button has been pressed that is not actualy on the calculator";
}
void computeP(calc a, char key) {
	if (a.opr[a.i] == '+') {
		a.n[a.i - 1] = a.n[a.i - 1] + a.n[a.i];
	}
	else if (a.opr[a.i] == '-') {
		a.n[a.i - 1] = a.n[a.i - 1] - a.n[a.i];
	}
	else if (a.opr[a.i] == '*') {
		a.n[a.i - 1] = a.n[a.i - 1] * a.n[a.i];
	}
	else if (a.opr[a.i] == '/') {
		a.n[a.i - 1] = a.n[a.i - 1] / a.n[a.i];
	}
	else
		cout << "ERROR \n";

		a.i--;


	std::cout << " or type opr to continue \n";
	std::cout << "IN: ";
	cin >> key;
	int x = buttonReader(key);
	if (x == 1) {
		buildNumberP(a, key);
	}
	else if (x == 2) {
		insertOpr(a, key);
	}
	else
		startPFIX(a, key);
}
	
double apply(calc a) {
	double temp =0;
	int i = a.i;
	for (int x = 0; x < i; x++) {
		if (a.opr[x] == '*') {
			a.n[x] = a.n[x] * a.n[x+1];
			for (int j = x + 1; j <= i; j++)
				a.n[j] = a.n[j+1];
			for (int j = x + 1; j <= i; j++)
				a.opr[j] = a.opr[j+1];

			i--; 
		}
		else if (a.opr[x] == '/') {
			a.n[x] = a.n[x] / a.n[x + 1];
			for (int j = x + 1; j <= i; j++)
				a.n[j] = a.n[j + 1];
			for (int j = x + 1; j <= i; j++)
				a.opr[j] = a.opr[j + 1];

			i--;
		}
		else {

		}
	}
	if (i < 1)
		i = 2;
	for (int x = 0; x < i; x++) {
		if (a.opr[x] == '+') {
			temp = a.n[x] + a.n[x + 1];
		}
		else{
			temp = a.n[x] - a.n[x + 1];
		}
	}
	return temp;
}

int buttonReader(char key) {
	if (isdigit(key)) {
		return (1); 
	}
	else if (isOpr(key)) {
		return (2);
	}
	else if (key == '=') {
		return (3);
	}
	else if (key == '.') {
		return (4);
	}
	else if (key == 'n') {
		return(5);
	}
	else if (key == 'T') {
		return (6); 
	}
	else {
		std::cout << "ERROR \n";
		return(0); 
	}
}
bool isOpr(char key) {
	if (key == '+') {
		return true;
	}
	else if (key == '-')
		return true;
	else if (key == '*')
		return true;
	else if (key == '/')
		return true;
	else
		return false;
}
int toInt(char key) {
	if (key == '0') {
		return 0;
	}
	else if (key == '1') {
		return 1;
	}
	else if (key == '2') {
		return 2;
	}
	else if (key == '3') {
		return 3;
	}
	else if (key == '4') {
		return 4;
	}
	else if (key == '5') {
		return 5;
	}
	else if (key == '6') {
		return 6;
	}
	else if (key == '7') {
		return 7;
	}
	else if (key == '8') {
		return 8;
	}
	else
		return 9;
}

/*
class calcMemory {
	public:
		double n[100] = {0.0};
		int dec = -1; 
		int i = 0;
		int ans = 0; 
		char opr[99] = {0};
		bool fix = true; 

		void buildNumber_Whole(int key, int i) {
			std::cout << "\n IN buildNuber_whole \n";
			std::cout <<n[i]<< key << i;
			if (n[i] == 0)
				n[i] = key;
			else
				n[i] = (n[i] * 10)+key;
		}
		void buildNumber_Decimal(int key, int i) {
			n[i] = n[i] + (pow(10, dec)*key);
			dec--;
		}
		double getNum(int i) {
			return n[i];
		}
		void setNum(double num, int i) {
			n[i] = num;
		}
		void increment_i() {
			i = i++;
		}
		void decrement_i() {
			i = i - 1;
		}
		int getI() {
			return i;
		}
		void setOpr(char key, int i) {
			opr[i] = key;
		}
		char getOpr(int i) {
			return opr[i];
		}
		void setMode() {
			fix = fix ^ 1;
		}
		void printMode() {
			if (fix == false)
				std::cout << "INFIX";
			else
				std::cout << "PREFIX";
		}
		bool getMode() {
			return fix;
		}
		void setAns(int sum) {
			ans = sum;
		}
		double getAns() {
			return ans;
		}
		void restart() {
			n[100] = { 0.0 };
			dec = -1;
			i = 0;
			opr[99] = {0};
			ans = 0; 
			setMode();
		}
};
void buttonPress(calcMemory calc, bool decimal);
void buttonPress_opr(calcMemory calc, bool decimal);
void restart(calcMemory calc);
bool isOpr(char key);
void number(calcMemory calc, int key, bool decimal);
void opr(calcMemory calc, int key, bool decimal);
void preCom(calcMemory calc);
void inCom(calcMemory calc);
void apply(calcMemory calc);
int toInt(char key);
int main() {
	calcMemory calc; 
	calc.printMode();
	cout << "\n Press n when you are done making your number in PREFIX \n";
		restart(calc);
	cin.ignore(); 
}

void restart(calcMemory calc) {
	calc.restart();
	calc.setMode();
	std::cout << "\n Now in ";
	calc.printMode();
	std::cout << '\n';
	buttonPress(calc, false);
}

void buttonPress(calcMemory calc, bool decimal) {
	char key;
	std::cout << "Waiting for num buttons to be pressed! (Please press one and then enter) \n";
	cin >> key;
	if (isdigit(key)) {
		std::cout << "\n NEW Digit \n";
		number(calc, toInt(key), decimal);
	}
	else if (key == '.') {
		decimal = true;
		buttonPress(calc, decimal);
	}
	else if (key == 'n') {
		std::cout << "\n NEW NUMBER \n";
		if (calc.getMode == false)
			buttonPress_opr(calc, decimal);
		else {
			calc.increment_i(); 
			buttonPress(calc, decimal);
		}
	}
	else if (key == '=') {
		inCom(calc);
	}
	else {
		std::cout << "ERROR \n";
		buttonPress(calc, decimal);
	}
}
void buttonPress_opr(calcMemory calc, bool decimal) {
	char key;
	std::cout << "Waiting for opr buttons to be pressed! (Please press one and then enter) \n";
	cin >> key;
	if (isOpr(key)) {
		std::cout << "\n NEW opr \n";
		opr(calc, key, decimal);
	}
	else {
		std::cout << "ERROR \n";
		buttonPress_opr(calc, decimal);
	}
}


void number(calcMemory calc, int key, bool decimal) {
	std::cout << "\n IN number \n";
	std::cout << calc.getNum(calc.getI()); 
	if (decimal == false) {
		calc.buildNumber_Whole(key, calc.getI());
		std::cout << "\n Number: ";
		std::cout << calc.getNum(calc.getI());
		std::cout << '\n';
	}
	else {
		calc.buildNumber_Decimal(key, calc.getI());
		std::cout << "\n Number: ";
		std::cout << calc.getNum(calc.getI());
		std::cout << '\n';
	}
	if (calc.getMode() == false)
		buttonPress_opr(calc, decimal);
	else 
		buttonPress(calc, decimal);
}
void opr(calcMemory calc, int key, bool decimal) {
	if (calc.getMode() == true) {
		calc.setOpr(key, calc.getI()-1);
		preCom(calc);
	}
	else {
		calc.setOpr(key, calc.getI());
		buttonPress(calc, decimal);
	}

}
void preCom(calcMemory calc){
	if (calc.getOpr(calc.getI() == '*'))
		calc.setAns(calc.getNum(calc.getI())*calc.getNum(calc.getI()-1));
	else if (calc.getOpr(calc.getI() == '/'))
		calc.setAns(calc.getNum(calc.getI())/calc.getNum(calc.getI() - 1));
	else if (calc.getOpr(calc.getI() == '+'))
		calc.setAns(calc.getNum(calc.getI()) + calc.getNum(calc.getI() - 1));
	else if (calc.getOpr(calc.getI() == '-'))
		calc.setAns(calc.getNum(calc.getI()) - calc.getNum(calc.getI() - 1));
	else {}
	calc.decrement_i();
	calc.setNum(calc.getAns(), calc.getI());
	calc.increment_i();
	std::cout << "\n Result: ";
	std::cout << calc.getAns(); 
	std::cout << "\n";
	buttonPress(calc, false);
}
void inCom(calcMemory calc) {
	apply(calc);
	std::cout << "\n Result: ";
	std::cout << calc.getAns();
	std::cout << "\n";
	buttonPress(calc, false);
}
void apply(calcMemory calc) {
	double temp; 
	int i = calc.getI();
	for (int x = 0; x < i; x++) {
		if (calc.getOpr(x) == '*') {
			temp = calc.getNum(x) * calc.getNum(x + 1);
			calc.setNum(0, x);
			calc.setNum(temp, x + 1);
			calc.setOpr('+', x);
		}
		else if (calc.getOpr(x) == '/') {
			temp = calc.getNum(x) / calc.getNum(x + 1);
			calc.setNum(0, x);
			calc.setNum(temp, x + 1);
			calc.setOpr('+', x);
		}
		else {

		}
	}
	for (int x = 0; x < i; x++) {
		if (calc.getOpr(x) == '+') {
			temp = calc.getAns() + calc.getNum(x);
			calc.setAns(temp);
		}
		else if (calc.getOpr(x) == '-') {
			temp = calc.getAns() - calc.getNum(x);
			calc.setAns(temp);
		}
		else {
			calc.setAns(calc.getNum(x));
		}
	}
}

bool isOpr(char key) {
	if (key == '+')
		return true;
	else if (key == '-')
		return true; 
	else if (key == '*')
		return true;
	else if (key == '/')
		return true;
	else
		return false;
}

int toInt(char key) {
	if (key == '0') {
		return 0;
	}
	else if (key == '1') {
		return 1;
	}
	else if (key == '2') {
		return 2;
	}
	else if (key == '3') {
		return 3;
	}
	else if (key == '4') {
		return 4;
	}
	else if (key == '5') {
		return 5;
	}
	else if (key == '6') {
		return 6;
	}
	else if (key == '7') {
		return 7;
	}
	else if (key == '8') {
		return 8;
	}
	else
		return 9;
}
*/