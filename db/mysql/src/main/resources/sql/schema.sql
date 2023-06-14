DROP TABLE IF EXISTS `inf_de_deployment`;
CREATE TABLE `inf_de_deployment` (
                                     `id` bigint(20) NOT NULL AUTO_INCREMENT,
                                     `rev` int(11) DEFAULT NULL,
                                     `version` int(11) DEFAULT NULL,
                                     `name` varchar(64) DEFAULT NULL,
                                     `key` varchar(64) DEFAULT NULL,
                                     `category` varchar(32) DEFAULT NULL,
                                     `deployment_id` varchar(64) DEFAULT NULL,
                                     `deployment_version_id` varchar(64) DEFAULT NULL,
                                     `remark` varchar(255) DEFAULT NULL,
                                     `meta_info` mediumtext,
                                     `deploy_by` varchar(64) DEFAULT NULL,
                                     `deploy_time` datetime DEFAULT NULL,
                                     `deploy_status` tinyint(1) DEFAULT NULL,
                                     `create_by` varchar(64) DEFAULT NULL,
                                     `create_time` datetime DEFAULT NULL,
                                     `update_by` varchar(64) DEFAULT NULL,
                                     `update_time` datetime DEFAULT NULL,
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `inf_ru_process_instance`;
CREATE TABLE `inf_ru_process_instance` (
                                           `id` int(11) NOT NULL AUTO_INCREMENT,
                                           `name` varchar(64) DEFAULT NULL,
                                           `deployment_id` varchar(64) DEFAULT NULL,
                                           `deployment_version_id` varchar(64) DEFAULT NULL,
                                           `process_instance_id` varchar(64) DEFAULT NULL,
                                           `start_by` varchar(64) DEFAULT NULL,
                                           `start_time` datetime DEFAULT NULL,
                                           `end_time` datetime DEFAULT NULL,
                                           `process_state` tinyint(4) DEFAULT NULL,
                                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;


CREATE TABLE `inf_ru_task_instance` (
                                        `id` int(11) NOT NULL AUTO_INCREMENT,
                                        `task_id` varchar(64) DEFAULT NULL,
                                        `execution_id` varchar(64) DEFAULT NULL,
                                        `process_instance_id` varchar(64) DEFAULT NULL,
                                        `element_type` varchar(16) DEFAULT NULL,
                                        `element_key` varchar(64) DEFAULT NULL,
                                        `element_name` varchar(64) DEFAULT NULL,
                                        `source_task_instance_id` varchar(64) DEFAULT NULL,
                                        `owner` varchar(64) DEFAULT NULL,
                                        `assignee` varchar(64) DEFAULT NULL,
                                        `assignee_type` varchar(16) DEFAULT NULL,
                                        `instance_state` tinyint(4) DEFAULT NULL,
                                        `start_time` datetime DEFAULT NULL,
                                        `end_time` datetime DEFAULT NULL,
                                        PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4;

DROP TABLE IF EXISTS `inf_ru_execution`;
CREATE TABLE `inf_ru_execution` (
                                    `id` int(11) NOT NULL AUTO_INCREMENT,
                                    `element_key` varchar(64) DEFAULT NULL,
                                    `execution_id` varchar(64) DEFAULT NULL,
                                    `process_instance_id` varchar(64) DEFAULT NULL,
                                    `deployment_version_id` varchar(64) DEFAULT NULL,
                                    `state` tinyint(4) DEFAULT NULL,
                                    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4;