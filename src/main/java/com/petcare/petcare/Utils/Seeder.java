package com.petcare.petcare.Utils;

import com.petcare.petcare.Exceptions.CouldNotSerializeException;
import com.petcare.petcare.Launcher;
import com.petcare.petcare.Services.Appointments;
import com.petcare.petcare.Services.Location;
import com.petcare.petcare.Services.Service;
import com.petcare.petcare.Services.ServiceType;
import com.petcare.petcare.Users.*;

public class Seeder {
    private static final boolean isServicesSeedable = Storage.getStorage().getServices().isEmpty();
    private static final boolean isUsersSeeadble = Storage.getStorage().getAdmins().isEmpty() ||
                                                Storage.getStorage().getClients().isEmpty() ||
                                                Storage.getStorage().getCompanies().isEmpty();

    private static final boolean isLocationsSeedable = Storage.getStorage().getLocations().isEmpty();

    private static final boolean isSeeded = !isUsersSeeadble && !isServicesSeedable && !isLocationsSeedable;

    /**
     *
     * Check if the storage is seeded
     *
     * @return True if seeded, false if not
     *
     */
    private static boolean isSeeded() {
        return isSeeded;
    }

    /**
     *
     * Seed the database with the default users and services
     *
     * @see #seedUsers() {@link #seedServices()}
     *
     */
    public static void seed() {
        if(isSeeded()) {
            Debug.warning("Storage already seeded! Skipping step...", true, true);
            return;
        }

        Debug.warning("Seeding storage...", true, true);

        if(isUsersSeeadble) {
            seedUsers();
            Debug.success("Users seeded!", true, true);
        }

        if (isServicesSeedable) {
            seedServices();
            Debug.success("Services seeded!", true, true);
        }

        if (isLocationsSeedable) {
            seedLocations();
            Debug.success("Locations seeded!", true, true);
        }

        Debug.success("Seeding complete...", true, true);

        try {
            Debug.warning("Serializing data into storage...", true, true);
            Storage.getStorage().serialize("./src/main/resources/data/storage.db");
        } catch (CouldNotSerializeException e) {
            Debug.print("Could not serialize storage! " + e.getMessage(), true, true);
            return;
        } finally {
            Debug.success("Data serialized successfully!", true, true);
        }
    }

    /**
     *
     * Seed the storage with the default users
     *
     */
    public static void seedUsers() {
        Admin admin = new Admin("admin", "admin");
        admin.setNif("123456789");
        admin.setName("Administrador");
        admin.setAddress("Rua do Administrador");
        admin.setEmail("admin@gmail.com");
        admin.setPhone(123456789);

        Client client = new Client("client", "client");
        client.setName("Cliente");
        client.setAddress("Rua do Cliente");
        client.setnif("123456789");
        client.setEmail("cliente@gmail.com");
        client.setPhone(123456789);

        Company company = new Company("987654321","Empresa", "empresa@gmail.com", "Rua da Empresa");
        Company company2 = new Company("123456789","Empresa2", "empresa2@gmail.com", "Rua da Empresa 2");

        Employee employee = new Employee("employee", "employee");
        employee.setName("Funcionário");
        employee.setSurname("Testeee");
        employee.setAddress("Rua do Funcionário");
        employee.setnif("123456789");
        employee.setEmail("employee@gmail.com");
        employee.setCompany(company);

        DeskEmployee employee1 = new DeskEmployee("employee1", "employee1");
        employee1.setName("Funcionário");
        employee1.setSurname("Testeee");
        employee1.setAddress("Rua do Funcionário");
        employee1.setnif("123456789");
        employee1.setEmail("employee1@gmail.com");

        Storage.getStorage().getAdmins().put(admin.getnif(), admin);
        Storage.getStorage().getClients().put(client.getnif(), client);
        Storage.getStorage().getCompanies().put(company.getnif(), company);
        Storage.getStorage().getCompanies().get(company.getnif()).addEmployee(employee);
        Storage.getStorage().getCompanies().put(company2.getnif(), company2);
        Storage.getStorage().getDeskEmployees().add(employee1);
    }

    /**
     *
     * Seed the storage with the default services
     *
     */
    public static void seedServices() {
        Debug.print(Storage.getStorage().getCompanyByName("Empresa").getName(), true, true);
        Service grooming = new Service("Tosquia", "Marque uma tosquia para o seu animal!", ServiceType.GROOMING, 15.00, Storage.getStorage().getCompanyByName("Empresa"));
        Service grooming1 = new Service("Tosquia", "Marque uma tosquia para o seu animal!", ServiceType.GROOMING, 25.00, Storage.getStorage().getCompanyByName("Empresa2"));
        Service bathing = new Service("Banho", "Marque um banho para o seu animal!", ServiceType.BATHING, 10.00, Storage.getStorage().getCompanyByName("Empresa"));
        Service veterinary = new Service("Veterinário", "Marque uma consulta para o seu animal!", ServiceType.VETERINARY, 20.00, Storage.getStorage().getCompanyByName("Empresa"));
        Service hotel = new Service("Hotel", "Marque uma estadia para o seu animal!", ServiceType.HOTEL, 25.00, Storage.getStorage().getCompanyByName("Empresa"));
        Service walking = new Service("Passeio", "Marque um passeio para o seu animal!", ServiceType.WALKING, 10.00, Storage.getStorage().getCompanyByName("Empresa"));
        Service training = new Service("Treino", "Marque um treino para o seu animal!", ServiceType.TRAINING, 15.00, Storage.getStorage().getCompanyByName("Empresa"));
        Service sitting = new Service("Babysitting", "Marque um serviço de babysitting para o seu animal!", ServiceType.SITTING, 25.00, Storage.getStorage().getCompanyByName("Empresa"));
        Service daycare = new Service("Creche", "Marque um serviço de creche para o seu animal!", ServiceType.DAYCARE, 45.00, Storage.getStorage().getCompanyByName("Empresa"));
        Service spa = new Service("Spa", "Marque um serviço de spa para o seu animal!", ServiceType.SPA, 25.00, Storage.getStorage().getCompanyByName("Empresa"));
        Service transport = new Service("Transporte", "Marque um serviço de transporte para o seu animal!", ServiceType.TRANSPORT, 7.50, Storage.getStorage().getCompanyByName("Empresa"));

        Appointments appointment1 = new Appointments("123456789", "Tosquia", "Rua do Funcionário", "Empresa", "2020-12-12", "15.00");

        Storage.getStorage().getServices().add(grooming);
        Storage.getStorage().getServices().add(grooming1);
        Storage.getStorage().getServices().add(bathing);
        Storage.getStorage().getServices().add(veterinary);
        Storage.getStorage().getServices().add(hotel);
        Storage.getStorage().getServices().add(walking);
        Storage.getStorage().getServices().add(training);
        Storage.getStorage().getServices().add(sitting);
        Storage.getStorage().getServices().add(daycare);
        Storage.getStorage().getServices().add(spa);
        Storage.getStorage().getServices().add(transport);
        Storage.getStorage().getAppointments().add(appointment1);
    }

    /**
     *
     * Seed the storage with the default locations
     *
     */
    public static void seedLocations() {
        Location location = new Location("Rua do Funcionário", "Porto", "1000-100", 123456789, Service.getTypeString(ServiceType.GROOMING), Storage.getStorage().getCompanyByName("Empresa2"));
        Location location1 = new Location("Rua do Funcionário 2", "Porto", "1000-100",123456789, Service.getTypeString(ServiceType.BATHING), Storage.getStorage().getCompanyByName("Empresa"));
        Location location2 = new Location("Rua do Funcionário 3", "Porto", "1000-100",123456789, Service.getTypeString(ServiceType.VETERINARY), Storage.getStorage().getCompanyByName("Empresa2"));
        Location location3 = new Location("Rua do Funcionário", "Porto", "1000-100", 123456789, Service.getTypeString(ServiceType.GROOMING), Storage.getStorage().getCompanyByName("Empresa"));

        Storage.getStorage().getLocations().add(location);
        Storage.getStorage().getLocations().add(location1);
        Storage.getStorage().getLocations().add(location2);
    }
}
