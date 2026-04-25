package com.maven.student.application.usecases;

import com.maven.student.application.dto.ObjectStudent;
import com.maven.student.application.dto.StudentDto;
import reactor.core.publisher.Mono;

/**
 * StudentPublisherService.
 * This interface defines the contract for publishing student information.
 *
 * @author Joseph Magallanes
 * @since 2025-11-30
 */
public interface StudentPublisherService {
    /**
     * Publishes the given StudentDto information.
     *
     * @param student the student information to publish
     * @return a Mono that completes when the publish operation is done
     */
    Mono<Void> publish(StudentDto student);

    /**
     * Publishes the given ObjectStudent information.
     *
     * @param student the student information to publish
     * @return a Mono that completes when the publish operation is done
     */
    Mono<Void> publishObject(ObjectStudent student);
}
