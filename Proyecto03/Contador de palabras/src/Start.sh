SECONDS=0
    
for i in {1..1000}; do java -cp InterfazGrafica.jar Main.InterfazGrafica;
    
ELAPSED="Elapsed: $(($SECONDS / 3600))hrs $((($SECONDS / 60) % 60))min $(($SECONDS % 60))sec"
echo $ELAPSED;
done