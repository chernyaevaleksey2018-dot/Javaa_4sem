Boolean b3 = Boolean.valueOf(true);
Boolean b4 = Boolean.valueOf("true");\
Boolean b5 = true;         
Boolean b6 = Boolean.parseBoolean("true"); 

Boolean b7 = Boolean.TRUE;
Boolean b8 = Boolean.FALSE;

Boolean b9 = Boolean.class.getDeclaredConstructor(boolean.class).newInstance(true);
Boolean b10 = Boolean.class.getDeclaredConstructor(String.class).newInstance("true");
