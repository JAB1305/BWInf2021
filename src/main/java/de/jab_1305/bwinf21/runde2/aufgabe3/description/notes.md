Man betrachtet die Hex-Zahl als ein geschlossenes System mit einer bestimmten Anzahl an "Balken" (Aktive Segmente der Anzeigen). <BR>
Um hierbei Zahlen übergreifendes Verschieben von Balken zu vereinfachen, schaltet man eine Arte "Pool" dazwischen, in welchen Balken temporär ausgelagert oder vorab herangezogen werden. Der Pool wird dabei durch die Summe der Bilanzen von Operationen dargestellt. <br>
Beispiel: <br>
MOVE 1 → 7 | B = -1 (Es wird ein Balken aus dem Pool beansprucht) <br>
MOVE 8 → A | B = 1 (Es wird ein Balken in den Pool ausgelagert) <br>
Nur wenn dieser Pool in Summe 0 ergibt, kann man all diese Operationen an der Zahl ausführen ohne dabei die genaue Bewegung der einzelnen Balken zu kennen oder das System zu verletzen.