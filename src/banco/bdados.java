package banco;
import javax.swing.*;
import java.sql.*;
import java.awt.*;
import java.awt.event.*;

public final class bdados extends JFrame implements ActionListener{

	/**
	 * Formulario
	 */
	JLabel 			lb_titulo, 
					lb_config_a, lb_config_ip, lb_config_db, lb_config_user, lb_config_pass, lb_config_st_a, lb_config_st_b,
					lb_manu_add_i, lb_manu_add_p, lb_manu_table, lb_manu_add_cod,
					lb_status_a;
	JTabbedPane  	master_panel;
	JPanel 			p_manager, p_config, p_status;
	JComboBox  	    jcb_config_bdados, jcb_manu_table;
	JTextField 	    tf_config_ip, tf_config_banco, tf_config_user, 
                            tf_manu_add_cod, tf_manu_add_item, tf_manu_add_preco;
        JPasswordField tf_config_pass;
        JTable          tab_dadossql;
	JButton			bt_config_con,
					bt_manu_add, bt_manu_search;
	static TextArea ta_status_a;
	logstatus 		lg;
	boolean         connected = false;
	ResultSet		res_con_database;
	Statement 		st_con_database;
	Connection 		in_con_database;
	String			url, driver_banco = "com.mysql.jdbc.Driver", usuario, senha;
        String[] colunas;
        String[][] dados;
	
	DatabaseMetaData dmd_estoque;
        int i;


	bdados(){

		setTitle("Aplicacao para banco de dados");
		setSize(800,550);
		setLocation(200,80);
		setResizable(false);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setBackground(new Color(224,224,224));
		getContentPane().setLayout(null); // não usar gerenciador de layout para personalizar posição 
										  // serve para poder usar o setBounds


		lb_titulo 	       = new JLabel("Controle de Estoque");

		lb_config_ip 	   = new JLabel("IP");
		lb_config_a        = new JLabel("Tipo de banco de dados");
		lb_config_db       = new JLabel("Banco de dados");
		lb_config_user     = new JLabel("Usuario");
		lb_config_pass     = new JLabel("Senha");
		lb_config_st_a 	   = new JLabel("Status: ");
		lb_config_st_b 	   = new JLabel();
                lb_manu_add_cod    = new JLabel("Código");
		lb_manu_add_i      = new JLabel("Nome do item");
		lb_manu_add_p      = new JLabel("Valor do item");
		lb_status_a 	   = new JLabel("Debug");
		lb_manu_table	   = new JLabel("Tabela");
		master_panel       = new JTabbedPane();

		p_manager          = new JPanel();
		p_config           = new JPanel();
		p_status		   = new JPanel();

		jcb_config_bdados       = new JComboBox();
		jcb_manu_table	   = new JComboBox();

		tf_config_ip	   = new JTextField();
		tf_config_banco	   = new JTextField();
		tf_config_user     = new JTextField();
		tf_config_pass     = new JPasswordField();
                
                tf_manu_add_cod      = new JTextField();
		tf_manu_add_item      = new JTextField();
		tf_manu_add_preco      = new JTextField();

		bt_config_con      = new JButton();
		bt_manu_add 	   = new JButton("Adicionar");
                bt_manu_search     = new JButton("Buscar");

		ta_status_a 		= new TextArea();

		ta_status_a.setEditable(false);
		
		bt_config_con.setText("Conectar");


		jcb_config_bdados.addItem("MySQL");
		jcb_config_bdados.addItem("Access");
		
		
		bt_config_con.addActionListener(this);
		bt_manu_add  .addActionListener(this);
		jcb_config_bdados .addActionListener(this);
		jcb_manu_table.addActionListener(this);
		
		p_config .setLayout(null);
		p_manager.setLayout(null);
		p_status .setLayout(null);

		lb_titulo			.setBounds(20, 10,200,20); 

		/* 					MANUTENÇÃO                     */
		lb_manu_table		.setBounds(30,10,200,20);
		jcb_manu_table		.setBounds(30,30,200,20);
                lb_manu_add_cod         .setBounds(30,50,200,20);
                tf_manu_add_cod         .setBounds(30,70,100,20);
		lb_manu_add_i           .setBounds(150,50,200,20);
		tf_manu_add_item	.setBounds(150,70,200,20);
		lb_manu_add_p           .setBounds(370,50,200,20);
		tf_manu_add_preco	.setBounds(370,70,100,20);
		bt_manu_search		.setBounds(30,110,100,25);
		bt_manu_add		.setBounds(150,110,100,25);
                
		/*                   -----                			*/

		/*  				CONFIGURAÇÕES                  */
		lb_config_ip		.setBounds(30, 30,200,20);
		tf_config_ip            .setBounds(30, 50,200,20);
		lb_config_a 		.setBounds(30, 70,200,20);
		jcb_config_bdados       .setBounds(30, 90,200,20); //  horizontal (x), vertical (y), largura, altura
		lb_config_db            .setBounds(30,110,200,20);
		tf_config_banco 	.setBounds(30,130,200,20);
		lb_config_user		.setBounds(30,150,200,20);
		tf_config_user		.setBounds(30,170,200,20);
		lb_config_pass		.setBounds(30,190,200,20);
		tf_config_pass		.setBounds(30,210,200,20); 
		bt_config_con		.setBounds(30,230,120,25);
		lb_config_st_a  	.setBounds(30,260,100,25);
		lb_config_st_b  	.setBounds(75,260,100,25);
		/*                   -----                			*/

		/*  				ESTATISTICAS                  */
		lb_status_a	          .setBounds(30, 30,200,20);
		ta_status_a               .setBounds(30,50,680,350);
		/*                   -----                			*/	

		master_panel		  .setBounds(20,50,750,450);

		lb_titulo       	  .setFont(new Font("Tahoma",0,12));

		lb_manu_add_i   	  .setFont(new Font("Tahoma",0,12));
		lb_manu_table   	  .setFont(new Font("Tahoma",0,12));
		lb_manu_add_p   	  .setFont(new Font("Tahoma",0,12));
		lb_manu_add_cod   	  .setFont(new Font("Tahoma",0,12));
		bt_manu_add     	  .setFont(new Font("Tahoma",0,12));
		bt_manu_search   	  .setFont(new Font("Tahoma",0,12));

		lb_config_a     	  .setFont(new Font("Tahoma",0,12));
		lb_config_db    	  .setFont(new Font("Tahoma",0,12));
		lb_config_user  	  .setFont(new Font("Tahoma",0,12));
		lb_config_pass  	  .setFont(new Font("Tahoma",0,12));
		bt_config_con   	  .setFont(new Font("Tahoma",0,12));
		lb_config_st_a   	  .setFont(new Font("Tahoma",0,12));

		lb_status_a			  .setFont(new Font("Tahoma",0,12));
		lb_config_ip		  .setFont(new Font("Tahoma",0,12));

		master_panel.addTab("Conectar"   ,p_config);
		master_panel.addTab("Manutenção"	  ,p_manager); 
		master_panel.addTab("Estatisticas" 	  ,p_status);

		p_manager	 .add(lb_manu_table);
		p_manager	 .add(jcb_manu_table);
                p_manager    .add(lb_manu_add_cod);
                p_manager    .add(tf_manu_add_cod);
		p_manager    .add(lb_manu_add_i);
		p_manager    .add(lb_manu_add_p);
		p_manager    .add(tf_manu_add_item);
		p_manager    .add(tf_manu_add_preco);
		p_manager	 .add(bt_manu_add);
                p_manager        .add(bt_manu_search);

		p_config	 .add(lb_config_ip);
		p_config	 .add(tf_config_ip);
		p_config	 .add(lb_config_a);
		p_config	 .add(jcb_config_bdados);
		p_config     .add(lb_config_db);
		p_config     .add(tf_config_banco);
		p_config     .add(lb_config_user);
		p_config     .add(tf_config_user);
		p_config     .add(lb_config_pass);
		p_config 	 .add(tf_config_pass);
		p_config     .add(bt_config_con);
		p_config	 .add(lb_config_st_a);
		p_config	 .add(lb_config_st_b);

		p_status 	 .add(ta_status_a);
		p_status	 .add(lb_status_a);

		getContentPane()   		.add(lb_titulo);
		getContentPane()   		.add(master_panel);
		
		lg = new logstatus("Iniciado.");
		lg.addNewEvent("\nO banco de dados atual selecionado é o " + jcb_config_bdados.getSelectedItem().toString());
		lg.addNewEvent("\nDriver "+ driver_banco);
		
		tf_config_banco.setText("estoque");
		tf_config_user .setText("root");
		tf_config_ip   .setText("127.0.0.1");
                
                                        tab_dadossql    = new JTable(dados, colunas);
                        tab_dadossql     .setBounds(30,150,680,250);
                        p_manager        .add(tab_dadossql); 

	
		enableControls(false);
		conn(3); // Desconectado.
	}

	
public static void main(String args[]){
		JFrame formulario = new bdados();
		formulario.setVisible(true);
	}

        @Override    
        public void actionPerformed(ActionEvent acao){    
		if(acao.getSource() == bt_config_con){ // conectar
			if(connected == false){
                            if(tf_config_ip.getText().isEmpty())
                                JOptionPane.showMessageDialog(null, "Você deve fornecer o nome do banco de dados.");
                            else if(tf_config_banco.getText().isEmpty())
                                JOptionPane.showMessageDialog(null, "Você deve fornecer o ip para efetuar a conexão.");
                            else{
                                	conn(2);
				lg.addNewEvent("\nConectando a " + tf_config_ip.getText());
				
				url          = "jdbc:mysql://" + tf_config_ip.getText() + "/" + tf_config_banco.getText();
				usuario 	 = tf_config_user.getText();
				senha		 = new String(tf_config_pass.getPassword());
                                
				lg.addNewEvent("\n" + url + ":" + usuario + ":" + senha);
				connect();
                            }
			}
			else
			{
				try{
					in_con_database.close();
					connected = false;
					enableControls(false);
					conn(3);
					lg.addNewEvent("\nDesconectado.");
				}
				catch(SQLException erro_sql){
					lg.addNewEvent("Erro ao desconectar-se: " + erro_sql);
				}
			}
			}
			else if(acao.getSource() == jcb_config_bdados){  // COMBOBOX COM BANCO DE DADOS
					if(jcb_config_bdados.getSelectedItem().equals("Access"))
					{
						driver_banco = "sun.jdbc.odbc.JdbcOdbcDriver";
					}
					if(jcb_config_bdados.getSelectedItem().equals("MySQL")){
						driver_banco = "com.mysql.jdbc.Driver";
					}
					lg.addNewEvent("\nO banco de dados atual selecionado é o " + jcb_config_bdados.getSelectedItem().toString());
					lg.addNewEvent("\nDriver "+ driver_banco);
				}
                        else if(acao.getSource() == jcb_manu_table){ // COMBOBOX DE TABELAS
                      if(connected == true){
                        int total_lin = 0, i = 0;
                            dados = null;
                            colunas = null;
                          if(!(tab_dadossql == null))  {
                              lg.addNewEvent("\nremovendo tabdados..");
                              tab_dadossql.removeAll();
                          p_manager.remove(tab_dadossql);
                          tab_dadossql = null;
                              
                          }
                          else
                              lg.addNewEvent("\nnão foi removido tabdados");
                        try{
                            colunas = new String[]{"Código","Nome","Valor"};
                        st_con_database = in_con_database.createStatement(); 
                        lg.addNewEvent("\n-> Tabela " + jcb_manu_table.getSelectedItem().toString()); 
                        res_con_database = st_con_database.executeQuery("SELECT * FROM " + jcb_manu_table.getSelectedItem().toString());
                       
                        res_con_database.last();
                        total_lin = res_con_database.getRow() -1;
                        res_con_database.first();
                        
                        dados = new String[total_lin][15];
                        
                        while(res_con_database.next()){
                            lg.addNewEvent("\nCódigo #"+ res_con_database.getInt("cod") + " :: " + res_con_database.getString("nome") +
                                            " valor: " + res_con_database.getFloat("valor"));
                            
                        
                        dados[i][0] = res_con_database.getString("cod");
                        dados[i][1] = res_con_database.getString("nome");
                        dados[i][2] = res_con_database.getString("valor");
                        i++;
                        }
                        }catch(SQLException erro_exec){
                                lg.addNewEvent("Erro!");
                               }
                      
                            }
                        }
			else if(acao.getSource() == bt_manu_add){ // ADICIONAR DADOS NA TABELA
                            
                            if(!(tf_manu_add_cod.getText().isEmpty() ||
                                 tf_manu_add_item.getText().isEmpty() || tf_manu_add_preco.getText().isEmpty())){
                            String atual_table = jcb_manu_table.getSelectedItem().toString();
                                String   insertSQLTable = "INSERT INTO " + atual_table + " (cod, nome, valor) VALUES ('" +
                                tf_manu_add_cod.getText() + "','" + tf_manu_add_item.getText() + "','" + 
                                tf_manu_add_preco.getText() + "');"; 
                
                                try (PreparedStatement stmt = in_con_database.prepareStatement(insertSQLTable)) 
                                {
                                    lg.addNewEvent("\nExecutando " + insertSQLTable + "...");
                                    stmt.execute();
                                    lg.addNewEvent("\nAdicionado " + tf_manu_add_item.getText() +
                                            " na tabela " + atual_table + "(código " + tf_manu_add_cod.getText() +
                                            ") pelo valor " + tf_manu_add_preco.getText() + ".");
                                }
                                catch(SQLException sqlerr){
                                    JOptionPane.showMessageDialog(null, "Um dos campos foi digitado incorretamente!");
                                lg.addNewEvent("\nErro ao adicionar: " + sqlerr);
                                }
                            }
                            else
                                JOptionPane.showMessageDialog(null, "Você precisa preencher todos os campos!");
                        }
}
public void connect(){
		try{
			Class.forName(driver_banco);
			in_con_database = DriverManager.getConnection(url, usuario, senha);
			conn(1);
			
			lg.addNewEvent("\nConectado a " + tf_config_ip.getText());
			dmd_estoque = in_con_database.getMetaData();
			res_con_database = dmd_estoque.getTables(null, null, null, null);
                        
			while(res_con_database.next())
				jcb_manu_table.addItem(res_con_database.getString(3));
                        connected = true;
                        enableControls(true);
                        
			
		}catch(ClassNotFoundException erro_con){
			JOptionPane.showMessageDialog(null, "Driver " + driver_banco + " não localizado.");
			lg.addNewEvent("\nDriver " + driver_banco + " não localizado.");
			conn(3);
		}catch(SQLException erro_sql){
                    lg.showError(erro_sql.getErrorCode());
                    lg.addNewEvent("\nErro: " + erro_sql);
                    conn(3);
		}
	}
public void conn(int status){
		switch(status){
		case 1:
			lb_config_st_b.setForeground(Color.green);
			lb_config_st_b.setText("Conectado.");
			break;
		case 2:
			lb_config_st_b.setForeground(Color.orange);
			lb_config_st_b.setText("Conectando...");
			break;
		case 3:
			lb_config_st_b.setForeground(Color.red);
			lb_config_st_b.setText("Desconectado.");
			break;
		}
	}
public void enableControls(boolean flag){
		if(flag == true){
			tf_config_ip	     .setEnabled(false);
			tf_config_pass		 .setEnabled(false);
			tf_config_user	     .setEnabled(false);
			tf_config_banco      .setEnabled(false);
			tf_manu_add_item 	 .setEnabled(true);
                        tf_manu_add_cod             .setEnabled(true);
			tf_manu_add_preco	 .setEnabled(true);
			bt_manu_add		 	 .setEnabled(true);
			jcb_manu_table	 	 .setEnabled(true);
			jcb_config_bdados	 .setEnabled(false);
                        bt_manu_search          .setEnabled(true);
			bt_config_con.setText("Desconectar");
		}
		else{
			jcb_manu_table   	 .removeAllItems();
                        tf_manu_add_item.setText("");
                        tf_manu_add_preco.setText("");
			tf_config_banco      .setEnabled(true);
			tf_config_ip	     .setEnabled(true);
			tf_config_pass		 .setEnabled(true);
			tf_config_user	     .setEnabled(true);
                        bt_manu_search      .setEnabled(false);
                        tf_manu_add_cod             .setEnabled(false);
			tf_manu_add_item 	 .setEnabled(false);
			tf_manu_add_preco	 .setEnabled(false);
			bt_manu_add		 	 .setEnabled(false);
			jcb_manu_table	 	 .setEnabled(false);
			jcb_config_bdados	 .setEnabled(true);
                        bt_config_con.setText("Conectar");
		}
                
	}
}