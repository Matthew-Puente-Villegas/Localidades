package presentacion;
import dominio.*;
import java.util.*;
public class Interfaz {
    private Pais pais = new Pais();
    private final Scanner teclado = new Scanner(System.in);
    public Interfaz() {
        pais = Pais.leer();
        procesarPeticion(leerPeticion());
    }


    //Métodos del interfaz
    public String[] leerPeticion() {
        System.out.print("Introduzca su petición: ");
        String cadena = teclado.nextLine();
        return cadena.split(" ");
    }
    public boolean procesarPeticion(String[] peticion) {
        if (peticion.length==1)
            if (peticion[0].equalsIgnoreCase("addProvincia"))
                annadirProvincia();
            else if (peticion[0].equalsIgnoreCase("addMunicipio"))
                annadirMunicipio();
            else if (peticion[0].equalsIgnoreCase("addLocalidad"))
                annadirLocalidad();
            else if (peticion[0].equalsIgnoreCase("list"))
                System.out.println(pais);
            else if (peticion[0].equalsIgnoreCase("read"))
                Pais.leer();
            else if (peticion[0].equalsIgnoreCase("exit")) {
                pais.grabar();
                return false;
            }
            else if (peticion[0].equalsIgnoreCase("ayuda"))
                ayuda();
            else {
                System.out.println("Petición errónea.\n");
                ayuda();
            }
        else {
            System.out.println("Petición erronea");
            ayuda();
        }
        return true; //En todos los casos debe seguir pidiendo y procesando peticiones
    }
    public static void ayuda() {
        System.out.println("""
                Introduzca una de las siguientes peticiones:
                addProvincia: Añadir una provincia.
                addMunicipio: Añadir un municipio.
                addLocalidad: Añadir una localidad.
                list: Listar el contenido.
                read: Lectura inicial.
                exit: Salir.
                """);
    }


    // Métodos "annadir"
    public void annadirProvincia() {
        System.out.print("Nombre de la provincia: ");
        String nombre = teclado.nextLine();
        pais.add(new Provincia(nombre));
        pais.grabar();
    }
    public void annadirMunicipio() {
        int numeroP;
        do {
            pais.listadoProvincias();
            System.out.print("Número de provincia: ");
            numeroP = teclado.nextInt();
            teclado.nextLine(); //Si no incluímos el nextLine, se guardará una línea vacía.

            if (numeroP <= (pais.size()+1)) {
                System.out.print("Nombre del municipio: ");
                String nombre = teclado.nextLine();
                pais.getProvincia(numeroP).add(new Municipio(nombre));
            } else {
                System.out.println("Número de provincia incorrecta. Inténtalo de nuevo.");
            }
        } while (numeroP > (pais.size()+1));
        pais.grabar();
    }
    public void annadirLocalidad() {
        int numeroP,numeroM;
        do {

            pais.listadoProvincias();
            System.out.print("Número de provincia: ");
            numeroP = teclado.nextInt();
            teclado.nextLine(); //Si no incluímos el nextLine, se guardará una línea vacía.

            if (numeroP <= (pais.size() + 1)) {
                do {

                    pais.getProvincia(numeroP).listadoMunicipios();
                    System.out.print("Número de provincia: ");
                    numeroM = teclado.nextInt();
                    teclado.nextLine(); //Si no incluímos el nextLine, se guardará una línea vacía.

                    if (numeroM <= (pais.getProvincia(numeroP).size() + 1)) {
                        System.out.print("Nombre de la localidad: ");
                        String nombre = teclado.nextLine();
                        System.out.print("Número de habitantes: ");
                        int habitantes = teclado.nextInt();
                        teclado.nextLine();
                        // Creamos la localidad
                        pais.getProvincia(numeroP).getMunicipio(numeroM).add(new Localidad(nombre, habitantes));
                    }
                    else {
                        System.out.println("Número de municipio incorrecto. Inténtalo de nuevo.");
                    }

                } while (numeroM > (pais.getProvincia(numeroP).size() + 1));
            } else {
                System.out.println("Número de provincia incorrecta. Inténtalo de nuevo.");
            }

        } while (numeroP > (pais.size()+1));
        pais.grabar();
    }
    public void grabar() {
        pais.grabar();
    }
    public void leer() {
        String nombreP, nombreM, nombreL;
        do {
            System.out.print("Introduce el nombre de la provincia (<enter> para finalizar): ");
            nombreP= teclado.nextLine();
            if (!nombreP.isEmpty()) {
                Provincia provincia = new Provincia(nombreP);

                do {
                    System.out.print("Introduce el nombre del municipio (<enter> para finalizar): ");
                    nombreM = teclado.nextLine();
                    if (!nombreM.isEmpty()) {
                        Municipio municipio = new Municipio(nombreM);

                        do {
                            System.out.print("Introduce el nombre de la localidad (<enter> para finalizar): ");
                            nombreL= teclado.nextLine();

                            if (!nombreL.isEmpty()){
                                System.out.print("Introduce la población de la localidad: ");
                                int poblacion= teclado.nextInt();
                                teclado.nextLine();
                                Localidad localidad=new Localidad(nombreL,poblacion);


                                municipio.add(localidad);
                            }

                        } while (!nombreL.isEmpty());
                        provincia.add(municipio);

                    }
                } while (!nombreM.isEmpty());
                pais.add(provincia);
                
            }
        }
        while (!nombreP.isEmpty());
    }
}