2)Object obj = "Hello";
System.out.println(obj instanceof String);   // true
System.out.println(obj instanceof Integer);  // false

obj = null;
System.out.println(obj instanceof Object);   // false
