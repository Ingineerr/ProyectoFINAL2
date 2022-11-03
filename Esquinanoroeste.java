
package esquinanoroeste;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


public class Esquinanoroeste extends javax.swing.JFrame {

   
    public Esquinanoroeste() {
        initComponents();
        this.setLocationRelativeTo(null);
        
    }
    /*Esta funcion comprueba si tanto la  sumatoria de las ofertas y demandas está balanceada y 
     no requiere de agregar ofertantes ni demandantes ficticios para balance. 
    */
    
    public double []comprobar(double m[][],int o,int d){
        double v[]=new double[4]; //se crea un vector conformado por un arreglo de 4 posiciones
        double so=0,sd=0; //sumatorias de oferta y demanda
        for(int i=0;i<o;i++){ //con este ciclo se suman los valores de las ofertas
            so=so+m[i][d-1]; 
            
        }
       
        for(int i=0;i<d;i++){ // con este ciclo se suman los valores de las demandas
            sd=sd+m[o-1][i];
        }
        if(sd!=so){ //si la sumatoria de la demanda es diferente de la sumatoria de la oferta y 
            //145  45
            if(sd>so){ //además la supera 
                o=o+1;//se compensa esto dando un ofertante más  para compensar la demanda. 
            }else{ //(Si la sumatoria de la oferta es superior a la sumatoria de la demanda)
                d=d+1;//se compensa esto dando un demandante más para cubrir la oferta
            }
        }
        if(sd!=so){
            v[0]=o; //se envia la cantidad de ofertantes, depende si se haya aumentado un ofertante o no. 
            v[1]=d; //se envia la cantidad de demandantes, depende si se haya aumentado un ofertante o no.
            v[2]=so; //se envia al vector la sumatoria de oferta para poderla mover a la columna siguiente.
            v[3]=sd; // se envia al vector la sumatoria de demanda para poderla mover a la fila siguiente.
        }else{
            v[0]=o; //se queda igual la cantidad de ofertantes
            v[1]=d; //se queda igual la cantidad de demandantes
            v[2]=0; //se queda la posición de la sumatoria de oferta tal como está, porque no hay cambios. 
            v[3]=0; //se queda la posición de la sumatoria de demanda tal como está, porque no hay cambios. 
        }
        return v; 
    }
    public double[][]crear_nueva_matriz(double m[][],int o,int d,double so,double sd,DefaultTableModel mo){
        double sobra=0;int i1=0,j1=0;
        double m1[][]=new double[o][d]; // se inicia otro arreglo bidimensional para crear la nueva matrix
        System.out.println(sd+"   "+so);
        if(sd>so){ //como la demanda supera la oferta se necesita agregar otro ofertante
            sobra=sd-so; //la diferencia entre la demanda y la oferta. 
            m1[o-2][d-1]=sobra; //se coloca el valor de la sobra que es la demanda faltante a cubrir 
            for(int i=0;i<o;i++){ //se inicia el recorrido en todas las filas de la matriz nueva
                j1=0; //jl tiene el lugar de las columnas "d" de la matriz original: "m"
                if(i!=o-2){ //esto es para evitar que toda la fila nueva agregada (la oferta compensatoria) se modifique
                    for(int j=0;j<d;j++){ //recorrido a través de las columnas. 
                        m1[i][j]=m[i1][j1];//se colocan en la matriz nueva los valores que tenia la matrix original
                        j1++; //Se incrementa el valor del indice, para seguir recorriendo las columnas de la matriz original. 
                    }i1++; // se incrementa el valor del indice para seguir recorriendo las filas de la matriz original
                }
            }
            
        }else{ //como la oferta supera la demanda se necesita agregar otro ofertante
            sobra=so-sd; //la diferencia entre la oferta y la demanda. 
            m1[o-1][d-2]=sobra; //se coloca el valor de la sobra que es la oferta faltante a cubrir 
            for(int i=0;i<o;i++){ //se inicia el recorrido en todas las filas de la matriz nueva
                j1=0; //jl tiene el lugar de las columnas "d" de la matriz original: "m"
                for(int j=0;j<d;j++){ //recorrido a través de las columnas. 
                    if(j!=d-2){ //esto es para evitar que toda la columna nueva agregada (la demanda compensatoria) se modifique
                        m1[i][j]=m[i1][j1]; //se colocan en la matriz nueva los valores que tenia la matrix original
                        j1++; //Se incrementa el valor del indice, para seguir recorriendo las columnas de la matriz original. 
                    }
                }
                i1++; // se incrementa el valor del indice para seguir recorriendo las filas de la matriz original
            }
        }
        
         mo.setRowCount(o); //se obtienen la cuenta de filas 
        mo.setColumnCount(d); //se obtienen la cuenta de columnas
        
        for(int i=0;i<o;i++){
            for(int j=0;j<d;j++){
                tablaEntrada.setValueAt(m1[i][j], i, j); //se vacian los datos de la matriz nueva a la tabla
            }
        }
        return m1;
    }
    public void operacion(double m1[][],int o,int d){
        double b[][]=new double[o][d]; //se crea una matriz que alberga el resultado de las unidades que se calculan en cada paso del metodo noroeste
        for(int i=0;i<o;i++){ //se hace el recorrido por las filas de la matriz 
            b[i][d-1]=m1[i][d-1]; //para vaciar los valores de las ofertas en otra matriz 
        }
         for(int i=0;i<d;i++){ //se hace el recorrido por las columnas de la matriz 
            b[o-1][i]=m1[o-1][i]; //para vaciar los valores de las demandas en otra matriz 
        }
        int pos=0;int c2=0;
        for(int i=0;i<o;i++){ //se recorren las filas 
            for(int j=pos;j<d;j++){//se buscan recorrer las columnas
                if(b[i][d-1]==b[o-1][j]){ //lo que se muestra en la oferta es igual a lo que se muestra en la primera demanda. 
                    b[i][j]=b[i][d-1]; //Se coloca en la esquina  el valor de la oferta 
                    b[i][d-1]=0; //La ultima oferta queda satisfecha 
                    b[o-1][j]=0; //La última demanda queda satisfecha 
                    if(i == o-2 && j == o-2){
                      pos=j;c2=1; //La posición de la columna se queda tal y como está, y se manda a activar el fin de ciclo I
                      break; //se sale del ciclo J 
                    }
                    else{
                      i++;  
                    }
                    
                    
                }else{//cuando no es así y 
                    if(b[i][d-1]<b[o-1][j]){//y la oferta en turno es menor que la demanda 
                             
                        b[o-1][j]=b[o-1][j]-b[i][d-1]; //el valor nuevo es la diferencia de la demanda con respecto a la oferta.
                        b[i][j]=b[i][d-1]; //se coloca en la esquina  el valor de la demanda completa de la columna.
                        b[i][d-1]=0; //se quita toda la oferta de esa fuente debido a que se ha cumplido. 
                        pos=j; //se conserva la posición de la columna, se pivotea debido a que esto indica que no se tiene que recorrer las columnas anexas aún
                        break; //para evadir el incremento de j en este turno 
                    }else{ //sino si la oferta en turno es mayor que la demanda
                        b[i][d-1]=b[i][d-1]-b[o-1][j];  //el valor nuevo es la diferencia de la oferta con respecto a la demanda.
                        b[i][j]=b[o-1][j]; //se coloca en la esquina  el valor de la oferta completa del renglón
                        b[o-1][j]=0; //se quita toda la demanda de ese destino debido a que se ha cumplido. 
                    }
                    
                } //Para este punto todavia se está en en el ciclo de J 
            }
            if(c2==1){break;} //se sale del ciclo I 
        }
        DefaultTableModel model = (DefaultTableModel) tablaResultados.getModel();
        model.setRowCount(o); //se indican los numeros de filas a insertar para la tabla de resultados
        model.setColumnCount(d); //se indican los numeros de columnas a insertar para la tabla de resultados
        double su=0; //Sumatoria del costo total 
        for(int i=0;i<o;i++){ //se recorren las filas 
            for(int j=0;j<d;j++){ //se recorren las columnas
             if(b[i][j]!=0){//Si el valor encontrado es diferente de cero, se muestra en la pantalla 
                    
             tablaResultados.setValueAt("[ "+b[i][j]+" , "+m1[i][j]+" ]", i, j);
             su=su+b[i][j]*m1[i][j];
            }else{
             tablaResultados.setValueAt(" - ", i, j);
             }
            }
        }
        lblCosto.setText("Costo Mínimo Total: "+su);
    }
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaEntrada = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        tablaResultados = new javax.swing.JTable();
        btnCalcular = new javax.swing.JButton();
        datDemanda = new javax.swing.JTextField();
        lbldatDemandantes = new javax.swing.JLabel();
        datOferta = new javax.swing.JTextField();
        lbldatOfertantes = new javax.swing.JLabel();
        btnCrearTabla = new javax.swing.JButton();
        lblCosto = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(51, 255, 0));

        tablaEntrada.setBackground(new java.awt.Color(255, 0, 0));
        tablaEntrada.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane2.setViewportView(tablaEntrada);

        tablaResultados.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {

            }
        ));
        jScrollPane3.setViewportView(tablaResultados);

        btnCalcular.setText("CALCULAR");
        btnCalcular.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCalcularActionPerformed(evt);
            }
        });

        lbldatDemandantes.setForeground(new java.awt.Color(255, 255, 255));
        lbldatDemandantes.setText("DATOS DEMANTANTES");
        lbldatDemandantes.setToolTipText("");

        lbldatOfertantes.setForeground(new java.awt.Color(255, 255, 255));
        lbldatOfertantes.setText("DATOS OFERENTES");
        lbldatOfertantes.setToolTipText("");

        btnCrearTabla.setText("CREAR TABLA");
        btnCrearTabla.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnCrearTablaActionPerformed(evt);
            }
        });

        lblCosto.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lblCosto.setForeground(new java.awt.Color(255, 255, 255));

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("METODO DE LA ESQUINA NOROESTE");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(163, 163, 163)
                        .addComponent(lblCosto, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(139, 139, 139)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lbldatDemandantes)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(32, 32, 32)
                                        .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 450, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addGap(40, 40, 40)
                                        .addComponent(datDemanda, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(lbldatOfertantes)
                                        .addGap(26, 26, 26)
                                        .addComponent(datOferta, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnCrearTabla)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(btnCalcular))))
                            .addComponent(jScrollPane2)
                            .addComponent(jScrollPane3))))
                .addContainerGap(168, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addComponent(jLabel5)
                .addGap(8, 8, 8)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbldatDemandantes)
                    .addComponent(datDemanda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(datOferta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lbldatOfertantes)
                    .addComponent(btnCrearTabla)
                    .addComponent(btnCalcular))
                .addGap(29, 29, 29)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblCosto, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                .addGap(21, 21, 21))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void btnCalcularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCalcularActionPerformed
       
        int d=Integer.parseInt(datDemanda.getText()); //captura el numero de las columnas a insertar a la matriz
        int o=Integer.parseInt(datOferta.getText()); //captura el número de las filas a insertar a la matriz
        double m[][]=new double[o][d]; //Se crea una matriz que tome todos los valores de las columnas y filas
        for(int i=0;i<o;i++){ //ciclo for para recorrer y hacer un parsing de los números escritos en las celdas de la tabla
            for(int j=0;j<d;j++){
                
                m[i][j]=Double.parseDouble(""+tablaEntrada.getValueAt(i, j));
            }
        }
         DefaultTableModel modeles = (DefaultTableModel) tablaEntrada.getModel(); //Se necesita pasar la tabla ya creada para poder crear una nueva matriz de ser necesario
        double v[]=comprobar(m,o,d); //se envia la matriz que tiene los numeros de la tabla, la cantidad de filas y columnas
        //Una vez comprobado que la sumatoria de demanda y la de oferta son iguales o no 
        o=(int)(v[0]); //Se necesita la cantidad de ofertantes debido a que se van a pasar a la siguiente matriz
        //o para realizar el calculo del costo total. 
        d=(int)(v[1]);//Se necesita la cantidad de demandantes debido a que se van a pasar a la siguiente matriz
        //d para realizar el calculo del costo total. 
        double so=0,sd=0;
        so=v[2]; //Se capturan las sumatorias de las ofertas de ser necesario aplicar una compensación de oferta y demanda
        sd=v[3];
        //System.out.println(o+" "+d);
        if(so!=sd){ //Si las sumatorias no fueran iguales, se tiene que crear una matriz que compense esto.
        double m2[][]=crear_nueva_matriz(m,o,d,so,sd,modeles);
        operacion(m2,o,d); //se procede a calcular los coeficientes para obtener el costo mínimo
        }else{
        operacion(m,o,d); //se procede a calcular los coeficientes para obtener el costo mínimo
        }
        
        
    }//GEN-LAST:event_btnCalcularActionPerformed

    private void btnCrearTablaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCrearTablaActionPerformed
        // TODO add your handling code here:
        DefaultTableModel modeles = (DefaultTableModel) tablaEntrada.getModel(); //se crea una instancia del objeto Tabla
        int d=Integer.parseInt(datDemanda.getText()); //se define la cantidad de columnas que representan los datos de las demandas de los destinos. 
        int o=Integer.parseInt(datOferta.getText()); //se define la cantidad de filas que representan los datos de las ofertas de los fuentes. 
        modeles.setRowCount(o);
        modeles.setColumnCount(d);

    }//GEN-LAST:event_btnCrearTablaActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(Esquinanoroeste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Esquinanoroeste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Esquinanoroeste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Esquinanoroeste.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Esquinanoroeste().setVisible(true);
               
                
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnCalcular;
    private javax.swing.JButton btnCrearTabla;
    private javax.swing.JTextField datDemanda;
    private javax.swing.JTextField datOferta;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JLabel lblCosto;
    private javax.swing.JLabel lbldatDemandantes;
    private javax.swing.JLabel lbldatOfertantes;
    private javax.swing.JTable tablaEntrada;
    private javax.swing.JTable tablaResultados;
    // End of variables declaration//GEN-END:variables
}
