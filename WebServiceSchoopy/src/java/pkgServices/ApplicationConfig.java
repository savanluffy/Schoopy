/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pkgServices;

import java.util.Set;
import javax.ws.rs.core.Application;

/**
 *
 * @author schueler
 */
@javax.ws.rs.ApplicationPath("webresources")
public class ApplicationConfig extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        return resources;
    }

    /**
     * Do not modify addRestResourceClasses() method. It is automatically
     * populated with all resources defined in the project. If required, comment
     * out calling this method in getClasses().
     */
    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(pkgServices.LessonService.class);
        resources.add(pkgServices.MessageService.class);
        resources.add(pkgServices.PrivateFileService.class);
        resources.add(pkgServices.PublicFileService.class);
        resources.add(pkgServices.RoomService.class);
        resources.add(pkgServices.SchoopyAdminService.class);
        resources.add(pkgServices.StudentService.class);
        resources.add(pkgServices.SubjectService.class);
        resources.add(pkgServices.TeacherService.class);
        resources.add(pkgServices.TeacherSpecializationService.class);

    }

}
