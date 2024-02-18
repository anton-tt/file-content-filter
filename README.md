# File Content Filter
## ������� ���������� ����������� ������.

��������� ��������� �� ������������ ����� � ��������� ����������. ��������� �� ��� ������ (� �������� ����������� 
��������� ��������� ������� ������), ��������� �� �� ��� ��������� - ����� �����, ������� ����� (� �.�. 
� ���������������� �����) � ������. ����� ���� ��������� ��� ������ ������ �� ��������� � ��������������� ����� - 
integers.txt, floats.txt, strings.txt .

��� ���������� ��������� ������������� ���� ���������������� Java (������ 11) � ������������������ ������� ������ 
Apache Maven (������ 3.9.4) . ��� ������ ������� ������� ���������� � ��������� ����� � ����� � ���������� � ��������� 
������ - �������� __mvn package__ . � ������ ������������� ������ ��������� ������ - �������� **mvn clean package** .

��� ������� �������, �������� � ����� � ���������� (*file-content-filter*), ���������� ������ � ��������� ������ - 
������� __java -jar target/util.jar__

��� ���������� ������ ��������� � ������ ����������� ���������������� ��������� ���� �� ���� ���� � �������. � ��������� 
����� �������� ������ ������� ����� ������������ ����� � ����������� .txt. ��� ������� �� ��������� ���������, ��� 
�����, ������� ���������� ����������, ��� ��������� � �������� src/main/resources. � ���� ������, ���������� �������� 
����� ������� ����� ��������� ������� ���/����� ���� ������. ��������, ��������� �������� ���� in1.txt: 
java -jar target/util.jar in1.txt .  
����� ��� ������ ����� ������� ������� ���������� ���� � �����, �������������� � ������ �����, ������ �������:  
java -jar target/util.jar C:\Users\user\Documents\Java\file-content-filter\src\main\resources\in1.txt

��� ������������� ����� ������� �������������� ����� ��� ��������� ���������� ������ (������������ ������� ��������,
��������� �������� � �� ������������ ������ � ����� ���):

1. **-a** �� ��������� ������� �������������� ������������ �������� ����, ��� ���� ��������� � ��� ���������� ���������. 
������ ����� ��������� ����� ������ � ��� ���������. ������ �������: java -jar target/util.jar -a in1.txt 

2. **-s** ����� ������� ���������� �� ������ �������. ������������ ���������� � �������, ������� ��������� ���� �������� 
� ������ �������� ����, ��������������� ������ �� ����� ������. ������ �������: java -jar target/util.jar -s in1.txt

3. **-f** ����� ������ ������ ���������� �� ������ �������. ������������ ���������� � �������, ������� ��������� ���� 
�������� � ������ �������� ����, ����� ��� ����� � ������� ����� ������������ � ����������� �������� ���������, ����� � 
������� ��������. ��� ����� ��������� ������ ����� �������� � ����� �������. ������ �������: 
java -jar target/util.jar -f in1.txt

4. **-p** ����� ��������� �������� � ������ �������� ������ �������. ��� ���������� ������ ������� ���������� �������� 
����� �������� ����� -� ����� ������ ����������� ������� � �������� _ . ������ �������: 
java -jar target/util.jar in1.txt -p result_  
*����������: � ����� ����� ����������� ��������� ������� ? < > " * + : | / \ . � ������ ������ ������ ����� � �������� 
������������� ��������, ����� ����� ��������� �� ��������� ��� ������ ������� - � ��������� default*_ .

5. **-o** �� ��������� ������� ��������� �������� ����� � �������� src/main/resources. ��� ������ ������ ����� ����� 
������� ��������� ������ ����� ��� ����������. ��� ���������� ������ ��������� ������� ���������� �������� ����� 
�������� ����� -o ����� ������ ���� � ����������� �����. ������ �������:  
java -jar target/util.jar in1.txt -o src\main\resources - ������������� ����.  
java -jar target/util.jar in1.txt -o C:\Users\user\Documents\Java\file-content-filter\src\main\resources - ���������� 
����.  
*����������: � ������ ������ ������ ����� � �������� ������������� ���� � �����, ����� ����� ��������� �� ��������� ��� 
������ ������� � ����� src/main/resources � ���������* . 

��� ������� ������� ��������� �������� ���������� ����� ����� ������� ������� java -jar target/util.jar � ���������� 
�����. ������������������ ����� � ������ �������, �� ������ ��������� �������, ��������, -o � ���� � ����� ������ ���� 
����� ����� ������.  
������ �������: java -jar target/util.jar in1.txt -o src\main\resources -f -p result_

*��������� ����������, ������� �������������� � ���������, ������� � ����� pom.xml . ������ ������� ����������� 
� �������� �� Windows 11.*

*� ������, ���� �� ���� ���� � ������� �� ����� ������ ����������, ������ ������� ����� ���������.  
� ������, ���� � ������ �� ����� ������� ������� ������, ������ ������� ����� ��������� ��� ������������ �������� 
������.  
� ������, ���� ��� ��������� ����� -p �� ����� ����� �������, ���� ����� �������� ��� ��������.  
� ������, ���� ��� ��������� ����� -o �� ����� ������ ����� �����, ���� ����� �������� � ����� src\main\resources.  
� ������, ���� ��� ���������� ������ �� ����� ������� ���� �� ����� ������, �������� ���� �� ���� �� ����������� / 
�� ����������������.*