// 3
Integer x = null;
int y = x;   //NullPointerException при автораспаковке null в int


void foo(int n) { }
foo(null);    //NPE, попытка распаковать null в int
