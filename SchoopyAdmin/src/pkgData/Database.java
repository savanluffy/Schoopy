/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgData;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import java.util.ArrayList;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author schueler
 */
public class Database {

    private static Database database;

    private final String uri = "http://localhost:8080/WebServiceSchoopy/webresources/";
    private Client client = null;
    private Gson gson = null;

    public static Database newInstance() throws Exception {
        if (database == null) {
            database = new Database();
        }
        return database;
    }

    private Database() throws Exception {
        client = Client.create();
        gson = new Gson();
    }

    public SchoopyAdmin login(SchoopyAdmin loginSA) throws Exception {
        SchoopyAdmin sa = null;
        WebResource resource = client.resource(uri + "schoppyadmins/login");

        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, gson.toJson(loginSA, SchoopyAdmin.class));
        String resAsString = response.getEntity(String.class);
        if (response.getStatus() != 200) {
            throw new Exception(resAsString);
        }
        sa = gson.fromJson(resAsString, SchoopyAdmin.class);
        return sa;
    }

    public ArrayList<Teacher> getAllTeachers() throws Exception {
        ClientResponse response = client.resource(uri + "teachers").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new Exception("Failed to load teachers!");
        }

        String roomsAsString = response.getEntity(String.class); //server sends gson.toJson which is a string and we receive it here
        return gson.fromJson(roomsAsString, new TypeToken<ArrayList<Teacher>>() {
        }.getType()); //new TypeToken is if you have a collection
    }

    public void addTeacher(Teacher newT) throws Exception {
        WebResource resource = client.resource(uri + "teachers/add");

        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, gson.toJson(newT, Teacher.class));
        if (response.getStatus() != 201) { //201=created     
            throw new Exception("add teacher failed:" + response.getEntity(String.class));
        }
    }

    public void updateTeacher(Teacher updateT) throws Exception {
        WebResource resource = client.resource(uri + "teachers/update");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, gson.toJson(updateT, Teacher.class));

        if (response.getStatus() != 200) {
            throw new Exception(response.getEntity(String.class));
        }
    }

    public void deleteTeacher(String username) throws Exception {
        WebResource resource = client.resource(uri + "teachers/" + username);
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new Exception(response.getEntity(String.class));
        }
    }

    public ArrayList<Teacher> filterTeachers(String newFilterValue) throws Exception {
        ClientResponse response = client.resource(uri + "teachers/filter/" + newFilterValue).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new Exception("Failed to filter teachers!");
        }

        String teachersAsString = response.getEntity(String.class);
        return gson.fromJson(teachersAsString, new TypeToken<ArrayList<Teacher>>() {
        }.getType());
    }

    public ArrayList<Subject> getAllSubjects() throws Exception {
        ClientResponse response = client.resource(uri + "subjects").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new Exception("Failed to load subjects!");
        }

        String subjectsAsString = response.getEntity(String.class);
        return gson.fromJson(subjectsAsString, new TypeToken<ArrayList<Subject>>() {
        }.getType());
    }

    public ArrayList<Subject> filterSubjects(String filterVal) throws Exception {
        ClientResponse response = client.resource(uri + "subjects/filter/" + filterVal).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new Exception("Failed to filter subjects!");
        }

        String subjectsAsString = response.getEntity(String.class);
        return gson.fromJson(subjectsAsString, new TypeToken<ArrayList<Subject>>() {
        }.getType());
    }

    public void addSubject(Subject newSubject) throws Exception {
        WebResource resource = client.resource(uri + "subjects/add");

        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, gson.toJson(newSubject, Subject.class));
        if (response.getStatus() != 201) { //201=created     
            throw new Exception("add subject failed:" + response.getEntity(String.class));
        }
    }

    public void updateSubject(Subject updateSubject) throws Exception {
        WebResource resource = client.resource(uri + "subjects");
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).put(ClientResponse.class, gson.toJson(updateSubject, Subject.class));

        if (response.getStatus() != 200) {
            throw new Exception(response.getEntity(String.class));
        }
    }

    public void deleteSubject(Subject selected) throws Exception {
        WebResource resource = client.resource(uri + "subjects/" + selected.getSubjectId());
        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new Exception(response.getEntity(String.class));
        }
    }

    public ArrayList<TeacherSpecialization> getAllTS() throws Exception {
        ClientResponse response = client.resource(uri + "teacherspecializations").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new Exception("Failed to load teacher specialization!");
        }

        String teacherSpecializationsAsString = response.getEntity(String.class);
        return gson.fromJson(teacherSpecializationsAsString, new TypeToken<ArrayList<TeacherSpecialization>>() {
        }.getType());
    }

    public ArrayList<TeacherSpecialization> getTSBySubject(Subject s) throws Exception {
        ClientResponse response = client.resource(uri + "teacherspecializations/allTeachers/"+s.getSubjectId()).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new Exception("Failed to load teacher specialization by subject!");
        }

        String teacherSpecializationsAsString = response.getEntity(String.class);
        return gson.fromJson(teacherSpecializationsAsString, new TypeToken<ArrayList<TeacherSpecialization>>() {
        }.getType());
    }

    public ArrayList<TeacherSpecialization> getTSByTeacher(Teacher t) throws Exception {
        ClientResponse response = client.resource(uri + "teacherspecializations/allSubjects/"+t.getUsername()).accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new Exception("Failed to load teacher specialization by teacher!");
        }

        String teacherSpecializationsAsString = response.getEntity(String.class);
        return gson.fromJson(teacherSpecializationsAsString, new TypeToken<ArrayList<TeacherSpecialization>>() {
        }.getType());
    }

    public void addTeacherSpecialization(TeacherSpecialization ts) throws Exception {
        WebResource resource = client.resource(uri + "teacherspecializations/add");

        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).post(ClientResponse.class, gson.toJson(ts, TeacherSpecialization.class));
        if (response.getStatus() != 201) {
            throw new Exception("add teacherspecialization failed:" + response.getEntity(String.class));
        }
    }

    public void deleteTeacherSpecialization(TeacherSpecialization ts) throws Exception {
        WebResource resource = client.resource(uri + "teacherspecializations/" + ts.getTeacher().getUsername() + "/" + ts.getSubject().getSubjectId());

        ClientResponse response = resource.type(MediaType.APPLICATION_JSON).delete(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new Exception(response.getEntity(String.class));
        }
    }

    public ArrayList<Room> getAllRooms() throws Exception {
        ClientResponse response = client.resource(uri + "rooms").accept(MediaType.APPLICATION_JSON).get(ClientResponse.class);

        if (response.getStatus() != 200) {
            throw new Exception("Failed to load rooms!");
        }

        String roomsAsString = response.getEntity(String.class);
        return gson.fromJson(roomsAsString, new TypeToken<ArrayList<Room>>() {
        }.getType());
    }

}
