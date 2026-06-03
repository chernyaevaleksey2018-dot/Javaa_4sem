// практика #1
int a=10,b=3;
//арифметика
int s=a+b,d=a-b,p=a*b,q=a/b,r=a%b;  // 13,7,30,3,1
//++ и -- префикс/постфикс
int x=5,pre=++x,post=x++;           // pre=6, x=7 (post=6)
int y=10,preD=--y,postD=y--;        // preD=9, y=8 (postD=9)
//составное присваивание
a+=5; a-=3; a*=2; a/=7; a%=4;
//сравнения
boolean eq=a==b, ne=a!=b, gt=a>b, lt=a<b, ge=a>=10, le=b<=3;
//логика
boolean f1=true,f2=false, and=f1&&f2, or=f1||f2, not=!f1;
//тернарный
int max=a>b?a:b;
//String конкатенация
String str="Sum:"+s, str2=a+","+b, str3=""+7+3, str4=7+3+"", str5="Hi,"+null;
