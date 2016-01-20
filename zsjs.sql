/*
Navicat MySQL Data Transfer

Source Server         : GXU
Source Server Version : 50540
Source Host           : localhost:3306
Source Database       : zsjs

Target Server Type    : MYSQL
Target Server Version : 50540
File Encoding         : 65001

Date: 2015-09-06 14:33:39
*/

SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for groups
-- ----------------------------
DROP TABLE IF EXISTS `groups`;
CREATE TABLE `groups` (
  `id` mediumint(6) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of groups
-- ----------------------------
INSERT INTO `groups` VALUES ('1', '项目组1');
INSERT INTO `groups` VALUES ('2', '项目组2');
INSERT INTO `groups` VALUES ('3', '项目组3');

-- ----------------------------
-- Table structure for n_admin
-- ----------------------------
DROP TABLE IF EXISTS `n_admin`;
CREATE TABLE `n_admin` (
  `userid` smallint(8) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(64) NOT NULL,
  `realname` varchar(50) NOT NULL,
  `password` char(32) NOT NULL,
  `roleid` int(11) DEFAULT '2',
  `encrypt` varchar(32) DEFAULT NULL,
  `lastloginip` varchar(40) DEFAULT NULL,
  `lastlogintime` int(11) unsigned DEFAULT '0',
  `email` varchar(50) NOT NULL,
  `hid` smallint(5) DEFAULT NULL,
  `bind_account` varchar(50) NOT NULL,
  `login_count` mediumint(8) unsigned DEFAULT '0',
  `tel` char(18) DEFAULT NULL,
  `remark` varchar(255) NOT NULL,
  `create_time` int(11) unsigned NOT NULL,
  `update_time` int(11) unsigned NOT NULL,
  `status` tinyint(1) DEFAULT '0',
  `type_id` tinyint(2) unsigned DEFAULT '0',
  `zhicheng` char(16) DEFAULT NULL,
  `gongling` char(16) DEFAULT NULL,
  `dmeduyear` char(8) DEFAULT NULL,
  `info` text NOT NULL,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `account` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=123 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of n_admin
-- ----------------------------
INSERT INTO `n_admin` VALUES ('122', 'test', 'test', 'd83dc0c813abc0db384e4bf88dae9e3b', '2', 'KBTcGk', '127.0.0.1', '1441502974', '', null, '', '0', null, '', '0', '0', '0', '0', null, null, null, '');
INSERT INTO `n_admin` VALUES ('121', 'Admin', '2', 'f7504d1ffddbee05b8ac680f5be11c1d', '0', 'UoIXzi', null, '0', '', null, '', '0', null, '', '0', '0', '0', '0', null, null, null, '');

-- ----------------------------
-- Table structure for n_admin1
-- ----------------------------
DROP TABLE IF EXISTS `n_admin1`;
CREATE TABLE `n_admin1` (
  `userid` mediumint(6) unsigned NOT NULL AUTO_INCREMENT,
  `username` varchar(20) DEFAULT NULL,
  `password` varchar(32) DEFAULT NULL,
  `roleid` smallint(5) DEFAULT '0',
  `encrypt` varchar(6) DEFAULT NULL,
  `lastloginip` varchar(15) DEFAULT NULL,
  `lastlogintime` int(10) unsigned DEFAULT '0',
  `email` varchar(40) DEFAULT NULL,
  `realname` varchar(50) NOT NULL DEFAULT '',
  PRIMARY KEY (`userid`),
  KEY `username` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of n_admin1
-- ----------------------------
INSERT INTO `n_admin1` VALUES ('1', 'admin', '39e690427418f1a0ffdf2930d549dc87', '1', 'Jgaznr', '0.0.0.0', '1439371570', 'admin@admin.com', '超级管理员');

-- ----------------------------
-- Table structure for n_admin_log
-- ----------------------------
DROP TABLE IF EXISTS `n_admin_log`;
CREATE TABLE `n_admin_log` (
  `logid` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `userid` mediumint(8) unsigned NOT NULL DEFAULT '0',
  `username` varchar(20) NOT NULL,
  `httpuseragent` text NOT NULL,
  `sessionid` varchar(30) NOT NULL,
  `ip` varchar(15) NOT NULL,
  `time` datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  `type` varchar(30) NOT NULL,
  PRIMARY KEY (`logid`),
  KEY `username` (`username`)
) ENGINE=MyISAM AUTO_INCREMENT=73 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of n_admin_log
-- ----------------------------
INSERT INTO `n_admin_log` VALUES ('1', '1', 'admin', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36', 'uqkpmvhq4h5a4c5skgdvhaffu2', '0.0.0.0', '2015-08-06 12:26:34', 'login');
INSERT INTO `n_admin_log` VALUES ('2', '1', 'admin', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36', 'uqkpmvhq4h5a4c5skgdvhaffu2', '0.0.0.0', '2015-08-06 12:33:04', 'login');
INSERT INTO `n_admin_log` VALUES ('3', '1', 'admin', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36', 'fq7rp68q4su7bfh93mh7heclq0', '0.0.0.0', '2015-08-07 09:16:40', 'login');
INSERT INTO `n_admin_log` VALUES ('4', '1', 'admin', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36', 'fq7rp68q4su7bfh93mh7heclq0', '0.0.0.0', '2015-08-07 09:31:46', 'login');
INSERT INTO `n_admin_log` VALUES ('5', '1', 'admin', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36', 'h5qv7o597k8ubomkhofroh2c11', '0.0.0.0', '2015-08-12 17:26:10', 'login');
INSERT INTO `n_admin_log` VALUES ('6', '1', 'admin', 'Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.81 Safari/537.36', 'h5qv7o597k8ubomkhofroh2c11', '0.0.0.0', '2015-08-12 17:35:31', 'login');
INSERT INTO `n_admin_log` VALUES ('7', '1', 'admin', 'Mozilla/5.0 (iPad; CPU OS 7_0 like Mac OS X) AppleWebKit/537.51.1 (KHTML, like Gecko) Version/7.0 Mobile/11A465 Safari/9537.53', 'h5qv7o597k8ubomkhofroh2c11', '0.0.0.0', '2015-08-12 17:54:08', 'login');
INSERT INTO `n_admin_log` VALUES ('8', '1', 'admin', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'fgnmfis434unbu05415v6loke6', '0.0.0.0', '2015-08-20 17:40:36', 'login');
INSERT INTO `n_admin_log` VALUES ('9', '1', 'admin', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'opc5f1fll49qjgqpqmdoa98n05', '0.0.0.0', '2015-08-21 10:12:28', 'login');
INSERT INTO `n_admin_log` VALUES ('10', '1', 'admin', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'nuie3330ca1mkigrnjr8244ut5', '0.0.0.0', '2015-08-21 14:54:12', 'login');
INSERT INTO `n_admin_log` VALUES ('11', '1', 'admin', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '45d5o235q30d91bmgktejqu951', '0.0.0.0', '2015-08-21 15:57:03', 'login');
INSERT INTO `n_admin_log` VALUES ('12', '1', 'admin', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '9i1241pci3te2eibgoont9jva7', '0.0.0.0', '2015-08-21 16:00:25', 'login');
INSERT INTO `n_admin_log` VALUES ('13', '1', 'admin', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '9m0bk491t1u91iifcgfnn8k611', '0.0.0.0', '2015-08-21 16:02:29', 'login');
INSERT INTO `n_admin_log` VALUES ('14', '1', 'admin', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '476bgs7jk7alqc62g5qphu0lk5', '0.0.0.0', '2015-08-22 10:46:31', 'login');
INSERT INTO `n_admin_log` VALUES ('15', '1', 'admin', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'jsggt2falis8vls7ou9opt9i12', '0.0.0.0', '2015-08-22 11:18:16', 'login');
INSERT INTO `n_admin_log` VALUES ('16', '1', 'admin', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '2b84cijfcgos9u7fefmmv0n8p1', '0.0.0.0', '2015-08-22 11:31:57', 'login');
INSERT INTO `n_admin_log` VALUES ('17', '1', 'admin', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'bbi398dk35ag7ue6sc6t99qhb1', '0.0.0.0', '2015-08-22 17:12:44', 'login');
INSERT INTO `n_admin_log` VALUES ('18', '1', 'admin', 'Mozilla/5.0 (Windows NT 6.1; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'kkbk6c8es9nfjceovd783ktkl1', '0.0.0.0', '2015-08-23 16:54:23', 'login');
INSERT INTO `n_admin_log` VALUES ('19', '1', 'admin', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'i316c1e5ic2m2rc2a3vljn6sd5', '0.0.0.0', '2015-08-24 09:31:27', 'login');
INSERT INTO `n_admin_log` VALUES ('20', '1', 'admin', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '32oei7hikmr97u8tca0b16csf4', '0.0.0.0', '2015-08-24 09:32:54', 'login');
INSERT INTO `n_admin_log` VALUES ('21', '1', 'admin', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'cntn2djjvr8ckqeg1kfgbj5t74', '0.0.0.0', '2015-08-24 09:51:29', 'login');
INSERT INTO `n_admin_log` VALUES ('22', '1', 'admin', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'rgl2cfkksdorjjmfaaeike3i43', '0.0.0.0', '2015-08-24 10:00:42', 'login');
INSERT INTO `n_admin_log` VALUES ('23', '1', 'admin', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'q5q9spl8no7ai6mbsorm64mmt0', '0.0.0.0', '2015-08-24 15:00:51', 'login');
INSERT INTO `n_admin_log` VALUES ('24', '1', 'admin', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'cggee966cv955f0eb0sp3hl6o5', '0.0.0.0', '2015-08-24 15:27:14', 'login');
INSERT INTO `n_admin_log` VALUES ('25', '1', 'admin', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'pm1ivl685reut0927295ui5ij3', '0.0.0.0', '2015-08-24 17:27:22', 'login');
INSERT INTO `n_admin_log` VALUES ('26', '117', '123', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '0ra6sqjoo7g2ro6p8vge9nqg23', '0.0.0.0', '2015-08-24 17:31:52', 'login');
INSERT INTO `n_admin_log` VALUES ('27', '1', 'admin', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '9hn6lohq7iikob2ijfaqm2riq2', '0.0.0.0', '2015-08-27 14:30:41', 'login');
INSERT INTO `n_admin_log` VALUES ('28', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'qf5q7f5mj90s68he30e0fbknd1', '0.0.0.0', '2015-08-27 14:34:03', 'login');
INSERT INTO `n_admin_log` VALUES ('29', '1', 'admin', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '4kndafa4f5aao19jdo4erk0ak3', '0.0.0.0', '2015-08-27 14:38:05', 'login');
INSERT INTO `n_admin_log` VALUES ('30', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '6pvq35e1eh7f80kdfiq0at0ut0', '0.0.0.0', '2015-08-27 14:39:14', 'login');
INSERT INTO `n_admin_log` VALUES ('31', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'ugesdou3t5h2pce46t7l00rut7', '0.0.0.0', '2015-08-27 14:44:40', 'login');
INSERT INTO `n_admin_log` VALUES ('32', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '52490vhmm299htbm2refuo17k3', '0.0.0.0', '2015-08-27 14:46:03', 'login');
INSERT INTO `n_admin_log` VALUES ('33', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '7e0uc2umvf8upcr2rsb9so6le0', '0.0.0.0', '2015-08-27 14:47:31', 'login');
INSERT INTO `n_admin_log` VALUES ('34', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'nkp5umskd5g27305ov0oppa915', '0.0.0.0', '2015-08-27 14:56:55', 'login');
INSERT INTO `n_admin_log` VALUES ('35', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'ijck5u0m8q44qf90fo2gs0k741', '0.0.0.0', '2015-08-27 14:57:35', 'login');
INSERT INTO `n_admin_log` VALUES ('36', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'kifivdr7h1lcef2dg8uusfuee3', '0.0.0.0', '2015-08-27 15:08:07', 'login');
INSERT INTO `n_admin_log` VALUES ('37', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'rf3mnpfcqeavics12o9j0punr0', '0.0.0.0', '2015-08-27 15:16:34', 'login');
INSERT INTO `n_admin_log` VALUES ('38', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'tevmre3hklhnrluh1dj5gt16n7', '0.0.0.0', '2015-08-27 15:17:58', 'login');
INSERT INTO `n_admin_log` VALUES ('39', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '9nkdtelhbrgcs6t2b0i2nb5e02', '0.0.0.0', '2015-08-27 15:20:45', 'login');
INSERT INTO `n_admin_log` VALUES ('40', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'sf8nq173l9jopasn4cmoj54254', '0.0.0.0', '2015-08-27 15:23:02', 'login');
INSERT INTO `n_admin_log` VALUES ('41', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'um8cbndsjcmdufu7n0gkdbgej7', '0.0.0.0', '2015-08-27 15:30:51', 'login');
INSERT INTO `n_admin_log` VALUES ('42', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'ae5cls5ltv1rluii0no4ae16c0', '0.0.0.0', '2015-08-27 15:33:23', 'login');
INSERT INTO `n_admin_log` VALUES ('43', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'ofl1nf90uf5q15r4tvh4u2v4d3', '0.0.0.0', '2015-08-27 15:46:10', 'login');
INSERT INTO `n_admin_log` VALUES ('44', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '6peelhfvn7g2i2unfi3l6gaab0', '0.0.0.0', '2015-08-28 10:49:32', 'login');
INSERT INTO `n_admin_log` VALUES ('45', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '9f7624ag63k88il9i8etcvvne6', '0.0.0.0', '2015-08-28 10:51:28', 'login');
INSERT INTO `n_admin_log` VALUES ('46', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'vvafer5cu4j5kuensjn9bvgc71', '0.0.0.0', '2015-08-28 11:00:52', 'login');
INSERT INTO `n_admin_log` VALUES ('47', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'vvafer5cu4j5kuensjn9bvgc71', '0.0.0.0', '2015-08-28 11:01:37', 'login');
INSERT INTO `n_admin_log` VALUES ('48', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'vvafer5cu4j5kuensjn9bvgc71', '0.0.0.0', '2015-08-28 11:01:52', 'login');
INSERT INTO `n_admin_log` VALUES ('49', '119', 'admins', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'iif724h27ehbcsp4kg4pgs1257', '0.0.0.0', '2015-08-28 11:21:14', 'login');
INSERT INTO `n_admin_log` VALUES ('50', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'iif724h27ehbcsp4kg4pgs1257', '0.0.0.0', '2015-08-28 11:21:25', 'login');
INSERT INTO `n_admin_log` VALUES ('51', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'ql7fgrn0suutq6odvlgt3odrc6', '0.0.0.0', '2015-08-31 15:28:17', 'login');
INSERT INTO `n_admin_log` VALUES ('52', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'ql7fgrn0suutq6odvlgt3odrc6', '0.0.0.0', '2015-08-31 15:38:25', 'login');
INSERT INTO `n_admin_log` VALUES ('53', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'lgr47s6m9nr88h4rm1iab9bq93', '0.0.0.0', '2015-08-31 17:06:53', 'login');
INSERT INTO `n_admin_log` VALUES ('54', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'ekr0bomukhjdgo2o3vrh4ngd07', '0.0.0.0', '2015-08-31 20:28:27', 'login');
INSERT INTO `n_admin_log` VALUES ('55', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'tmkimtgqhdqc3mb690kid77nk3', '0.0.0.0', '2015-09-01 19:30:09', 'login');
INSERT INTO `n_admin_log` VALUES ('56', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '5aspdbrdpcomk19lbuf5t3k130', '0.0.0.0', '2015-09-01 21:29:43', 'login');
INSERT INTO `n_admin_log` VALUES ('57', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'g7kvtqn2krk007rcd79tkiliq4', '0.0.0.0', '2015-09-01 22:23:44', 'login');
INSERT INTO `n_admin_log` VALUES ('58', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '8gnm62s39bp8p94j5aputgm9i7', '0.0.0.0', '2015-09-02 14:02:51', 'login');
INSERT INTO `n_admin_log` VALUES ('59', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'la2mhko4q202l9fp5mgnja2mn5', '0.0.0.0', '2015-09-02 17:03:38', 'login');
INSERT INTO `n_admin_log` VALUES ('60', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'ubnct1ifcfpna48sanftf26m04', '0.0.0.0', '2015-09-02 19:51:25', 'login');
INSERT INTO `n_admin_log` VALUES ('61', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '7jkfh88rajua0acv2pt9q6ije2', '0.0.0.0', '2015-09-03 19:12:11', 'login');
INSERT INTO `n_admin_log` VALUES ('62', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '0o6ed32b9s49ibvcg9rdm14g03', '0.0.0.0', '2015-09-03 20:52:15', 'login');
INSERT INTO `n_admin_log` VALUES ('63', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'ol895h0f3335b79vpgvldmk1i4', '0.0.0.0', '2015-09-03 23:26:55', 'login');
INSERT INTO `n_admin_log` VALUES ('64', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '92n0s81t11l2te9d7o5qc4hdo4', '0.0.0.0', '2015-09-03 23:46:17', 'login');
INSERT INTO `n_admin_log` VALUES ('65', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'rto2irq9kvg16bd50r4lajt797', '0.0.0.0', '2015-09-04 16:16:58', 'login');
INSERT INTO `n_admin_log` VALUES ('66', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.152 Safari/537.36', 'dktsr0rdtsggm9nb57a6uoj3l6', '0.0.0.0', '2015-09-04 17:02:46', 'login');
INSERT INTO `n_admin_log` VALUES ('67', '120', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'h70pkveifcnpsjn6dua7q2gro5', '0.0.0.0', '2015-09-05 18:24:41', 'login');
INSERT INTO `n_admin_log` VALUES ('68', '122', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', 'eohlhv6471aina7d3pp2immfe7', '0.0.0.0', '2015-09-05 19:17:33', 'login');
INSERT INTO `n_admin_log` VALUES ('69', '122', 'test', 'Mozilla/5.0 (Windows NT 10.0; WOW64; rv:40.0) Gecko/20100101 Firefox/40.0', '43mpea1j2njprqiup1ikok8gd6', '0.0.0.0', '2015-09-05 19:44:10', 'login');
INSERT INTO `n_admin_log` VALUES ('70', '122', 'test', 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36', 'l3sldbsc3r4hrbb8ccabrcsq66', '127.0.0.1', '2015-09-05 23:57:48', 'login');
INSERT INTO `n_admin_log` VALUES ('71', '122', 'test', 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36', 'l3sldbsc3r4hrbb8ccabrcsq66', '127.0.0.1', '2015-09-05 23:59:07', 'login');
INSERT INTO `n_admin_log` VALUES ('72', '122', 'test', 'Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/43.0.2357.65 Safari/537.36', 'qi4g1drqe3q7ko4gl5jvj7om44', '127.0.0.1', '2015-09-06 09:29:34', 'login');

-- ----------------------------
-- Table structure for n_admin_role
-- ----------------------------
DROP TABLE IF EXISTS `n_admin_role`;
CREATE TABLE `n_admin_role` (
  `roleid` tinyint(3) unsigned NOT NULL AUTO_INCREMENT,
  `rolename` varchar(50) NOT NULL,
  `description` text NOT NULL,
  `listorder` smallint(5) unsigned NOT NULL DEFAULT '0',
  `disabled` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`roleid`),
  KEY `listorder` (`listorder`),
  KEY `disabled` (`disabled`)
) ENGINE=MyISAM AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of n_admin_role
-- ----------------------------
INSERT INTO `n_admin_role` VALUES ('1', '超级管理员', '超级管理员', '1', '0');
INSERT INTO `n_admin_role` VALUES ('5', '题库录入组', '负责录入题库', '1', '0');
INSERT INTO `n_admin_role` VALUES ('3', '成绩统计组', '可以查看每个成绩', '1', '0');

-- ----------------------------
-- Table structure for n_admin_role_priv
-- ----------------------------
DROP TABLE IF EXISTS `n_admin_role_priv`;
CREATE TABLE `n_admin_role_priv` (
  `roleid` tinyint(3) unsigned NOT NULL DEFAULT '0',
  `c` varchar(20) NOT NULL,
  `a` varchar(20) NOT NULL,
  KEY `roleid` (`roleid`,`c`,`a`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of n_admin_role_priv
-- ----------------------------
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Admin', 'left');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Admin', 'memberAdd');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Admin', 'memberDelete');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Admin', 'memberEdit');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Admin', 'memberList');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Admin', 'memberResetPassword');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Admin', 'memberViewList');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Grade', 'gradeList');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Main', 'index');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Menu', 'menuList');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Question', 'questionList');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'System', 'menuAdd');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'System', 'menuDelete');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'System', 'menuEdit');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'System', 'menuExport');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'System', 'menuImport');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'System', 'menuOrder');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'System', 'menuViewList');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'System', 'settingLeft');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'System', 'top');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Test', 'questionAdd');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Test', 'questionDelect');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Test', 'questionEdit');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Test', 'questionList');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Test', 'singleAdd');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Test', 'testAdd');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Test', 'testDelect');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Test', 'testEdit');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Test', 'testList');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'Test', 'testList');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'UserInfo', 'userAdd');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'UserInfo', 'userDelect');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'UserInfo', 'userEdit');
INSERT INTO `n_admin_role_priv` VALUES ('2', 'UserInfo', 'userInfoList');

-- ----------------------------
-- Table structure for n_grade
-- ----------------------------
DROP TABLE IF EXISTS `n_grade`;
CREATE TABLE `n_grade` (
  `id` int(10) NOT NULL AUTO_INCREMENT COMMENT '成绩id',
  `userid` int(10) DEFAULT NULL COMMENT '用户id',
  `test_id` varchar(20) DEFAULT NULL COMMENT '考试名字',
  `single_code` int(5) DEFAULT NULL COMMENT '单选分数',
  `multy_code` int(5) DEFAULT NULL COMMENT '多选分数',
  `code` int(5) DEFAULT NULL COMMENT '总分',
  `time` int(2) DEFAULT NULL COMMENT '考试时间',
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=10 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of n_grade
-- ----------------------------
INSERT INTO `n_grade` VALUES ('9', '36', '42', '0', '0', '0', '0');

-- ----------------------------
-- Table structure for n_menu
-- ----------------------------
DROP TABLE IF EXISTS `n_menu`;
CREATE TABLE `n_menu` (
  `id` smallint(6) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(40) NOT NULL DEFAULT '',
  `parentid` smallint(6) NOT NULL DEFAULT '0',
  `c` varchar(20) NOT NULL DEFAULT '',
  `a` varchar(20) NOT NULL DEFAULT '',
  `data` varchar(255) NOT NULL DEFAULT '',
  `listorder` smallint(6) unsigned NOT NULL DEFAULT '0',
  `display` enum('1','0') NOT NULL DEFAULT '1',
  PRIMARY KEY (`id`),
  KEY `listorder` (`listorder`),
  KEY `parentid` (`parentid`),
  KEY `module` (`c`,`a`)
) ENGINE=MyISAM AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of n_menu
-- ----------------------------
INSERT INTO `n_menu` VALUES ('1', '我的面板', '0', 'Admin', 'top', '', '1', '1');
INSERT INTO `n_menu` VALUES ('2', '主页', '0', 'System', 'top', '', '2', '1');
INSERT INTO `n_menu` VALUES ('6', '安全记录', '1', 'Admin', 'userLeft', '', '0', '1');
INSERT INTO `n_menu` VALUES ('7', '登录日志', '6', 'Admin', 'loginLog', '', '1', '1');
INSERT INTO `n_menu` VALUES ('8', '删除登录日志', '7', 'Admin', 'loginLogDelete', '', '1', '1');
INSERT INTO `n_menu` VALUES ('9', '系统设置', '2', 'System', 'settingLeft', '', '1', '1');
INSERT INTO `n_menu` VALUES ('10', '系统设置', '9', 'System', 'setting', '', '1', '1');
INSERT INTO `n_menu` VALUES ('11', '菜单设置', '9', 'System', 'menuList', '', '2', '1');
INSERT INTO `n_menu` VALUES ('12', '查看列表', '11', 'System', 'menuViewList', '', '0', '1');
INSERT INTO `n_menu` VALUES ('13', '添加菜单', '11', 'Menu', 'menuAdd', '', '0', '1');
INSERT INTO `n_menu` VALUES ('14', '修改菜单', '11', 'Meun', 'menuEdit', '', '0', '1');
INSERT INTO `n_menu` VALUES ('15', '删除菜单', '11', 'System', 'menuDelete', '', '0', '1');
INSERT INTO `n_menu` VALUES ('16', '菜单排序', '11', 'System', 'menuOrder', '', '0', '1');
INSERT INTO `n_menu` VALUES ('17', '菜单导出', '11', 'System', 'menuExport', '', '0', '1');
INSERT INTO `n_menu` VALUES ('18', '菜单导入', '11', 'System', 'menuImport', '', '0', '1');
INSERT INTO `n_menu` VALUES ('19', '后台用户设置', '2', 'Admin', 'left', '', '2', '1');
INSERT INTO `n_menu` VALUES ('20', '后台用户管理', '19', 'Admin', 'memberList', '', '1', '1');
INSERT INTO `n_menu` VALUES ('21', '查看后台列表', '20', 'Admin', 'memberViewList', '', '0', '1');
INSERT INTO `n_menu` VALUES ('22', '添加后台用户', '20', 'Admin', 'memberAdd', '', '0', '1');
INSERT INTO `n_menu` VALUES ('23', '编辑后台用户', '20', 'Admin', 'memberEdit', '', '0', '1');
INSERT INTO `n_menu` VALUES ('24', '删除后台用户', '20', 'Admin', 'memberDelete', '', '0', '1');
INSERT INTO `n_menu` VALUES ('85', '用户添加', '84', 'UserInfo', 'userAdd', '', '0', '1');
INSERT INTO `n_menu` VALUES ('86', '编辑用户', '84', 'UserInfo', 'userEdit', '', '0', '1');
INSERT INTO `n_menu` VALUES ('83', '用户管理', '2', 'Main', 'index', '', '0', '1');
INSERT INTO `n_menu` VALUES ('84', '用户信息', '83', 'UserInfo', 'userInfoList', '', '0', '1');
INSERT INTO `n_menu` VALUES ('34', '日志管理', '33', 'System', 'logList', '', '3', '1');
INSERT INTO `n_menu` VALUES ('35', '查看列表', '34', 'System', 'logViewList', '', '0', '1');
INSERT INTO `n_menu` VALUES ('36', '删除日志', '34', 'System', 'logDelete', '', '0', '1');
INSERT INTO `n_menu` VALUES ('37', '缓存管理', '33', 'System', 'fileList', '', '1', '1');
INSERT INTO `n_menu` VALUES ('61', '重置密码', '20', 'Admin', 'memberResetPassword', '', '0', '1');
INSERT INTO `n_menu` VALUES ('63', '邮件模版', '9', 'System', 'email', '', '3', '1');
INSERT INTO `n_menu` VALUES ('64', '模版添加', '63', 'System', 'emailAdd', '', '0', '1');
INSERT INTO `n_menu` VALUES ('65', '模版编辑', '63', 'System', 'emailEdit', '', '0', '1');
INSERT INTO `n_menu` VALUES ('66', '模版删除', '63', 'System', 'emailDelete', '', '0', '1');
INSERT INTO `n_menu` VALUES ('67', '模版列表', '63', 'System', 'emailList', '', '0', '1');
INSERT INTO `n_menu` VALUES ('68', '上传管理', '38', 'Storage', 'index', '', '0', '1');
INSERT INTO `n_menu` VALUES ('87', '删除用户', '84', 'UserInfo', 'userDelect', '', '0', '1');
INSERT INTO `n_menu` VALUES ('88', '成绩信息', '83', 'Grade', 'gradeList', '', '0', '1');
INSERT INTO `n_menu` VALUES ('89', '试题管理', '2', 'Question', 'questionList', '', '0', '1');
INSERT INTO `n_menu` VALUES ('75', '菜单管理', '9', 'Menu', 'menuList', '', '0', '1');
INSERT INTO `n_menu` VALUES ('90', '考试列表', '89', 'Test', 'testList', '', '0', '1');
INSERT INTO `n_menu` VALUES ('91', '增加考试', '90', 'Test', 'testAdd', '', '0', '1');
INSERT INTO `n_menu` VALUES ('92', '编辑考试', '90', 'Test', 'testEdit', '', '0', '1');
INSERT INTO `n_menu` VALUES ('93', '删除考试', '90', 'Test', 'testDelect', '', '0', '1');
INSERT INTO `n_menu` VALUES ('96', '增加题目', '90', 'Test', 'questionAdd', '', '0', '1');

-- ----------------------------
-- Table structure for n_question
-- ----------------------------
DROP TABLE IF EXISTS `n_question`;
CREATE TABLE `n_question` (
  `question_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '题目id',
  `question` varchar(80) DEFAULT NULL COMMENT '问题详细',
  `option1` varchar(80) DEFAULT NULL COMMENT '选项1',
  `option2` varchar(80) DEFAULT NULL,
  `option3` varchar(80) DEFAULT NULL,
  `option4` varchar(80) DEFAULT NULL,
  `option5` varchar(80) DEFAULT NULL,
  `test_id` int(20) DEFAULT NULL COMMENT '测试id',
  `answer` varchar(32) DEFAULT NULL COMMENT '答案',
  PRIMARY KEY (`question_id`)
) ENGINE=MyISAM AUTO_INCREMENT=43 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of n_question
-- ----------------------------
INSERT INTO `n_question` VALUES ('36', '436654', '56', '456', '456', '76', '', '34', '2,4');
INSERT INTO `n_question` VALUES ('37', '梵蒂冈和健康', '4', '5', '6', '7', '', '34', '3,4');
INSERT INTO `n_question` VALUES ('38', '53643', '5432', '452', '5432', '54325', '432', '34', '2,1');
INSERT INTO `n_question` VALUES ('39', '撒', '的撒', '啊', ' 撒', ' 的撒', '发的撒', '0', null);
INSERT INTO `n_question` VALUES ('40', '热热热 ', '342', '', '发的规划', '', '', '34', null);
INSERT INTO `n_question` VALUES ('41', '456', '76', '67', '67', '67', '67', '34', null);
INSERT INTO `n_question` VALUES ('42', 'test', '654', '465', '465', '6543', '6543', '34', '1');

-- ----------------------------
-- Table structure for n_question_multy
-- ----------------------------
DROP TABLE IF EXISTS `n_question_multy`;
CREATE TABLE `n_question_multy` (
  `question_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '题目id',
  `question` varchar(80) DEFAULT NULL COMMENT '问题详细',
  `option1` varchar(80) DEFAULT NULL COMMENT '选项1',
  `option2` varchar(80) DEFAULT NULL,
  `option3` varchar(80) DEFAULT NULL,
  `option4` varchar(80) DEFAULT NULL,
  `option5` varchar(80) DEFAULT NULL,
  `test_id` int(20) DEFAULT NULL COMMENT '测试id',
  `answer` varchar(32) DEFAULT NULL COMMENT '答案',
  PRIMARY KEY (`question_id`)
) ENGINE=MyISAM AUTO_INCREMENT=48 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of n_question_multy
-- ----------------------------
INSERT INTO `n_question_multy` VALUES ('46', '在消费活动中，人们一般借助( )等感觉来接受商品的各种信息', '视觉', '听觉', '嗅觉', '触觉', '味觉', '42', 'A,B,C');
INSERT INTO `n_question_multy` VALUES ('47', '人的心理活动过程包括( )', '意识过程', '意志过程', '非意识过程', '情感过程', '认识过程', '42', 'A,B,C');

-- ----------------------------
-- Table structure for n_question_single
-- ----------------------------
DROP TABLE IF EXISTS `n_question_single`;
CREATE TABLE `n_question_single` (
  `question_id` int(10) NOT NULL AUTO_INCREMENT COMMENT '题目id',
  `question_num` int(10) DEFAULT NULL COMMENT '题号',
  `question` varchar(80) DEFAULT NULL COMMENT '问题详细',
  `option1` varchar(80) DEFAULT NULL COMMENT '选项1',
  `option2` varchar(80) DEFAULT NULL,
  `option3` varchar(80) DEFAULT NULL,
  `option4` varchar(80) DEFAULT NULL,
  `test_id` int(20) DEFAULT NULL COMMENT '测试id',
  `answer` varchar(32) DEFAULT NULL COMMENT '答案',
  PRIMARY KEY (`question_id`)
) ENGINE=MyISAM AUTO_INCREMENT=45 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of n_question_single
-- ----------------------------
INSERT INTO `n_question_single` VALUES ('43', null, '割让香港岛给英国的不平等条约是？', '马关条约', '南京条约', '北京条约', '辛丑条约', '42', 'A');
INSERT INTO `n_question_single` VALUES ('44', null, '《南京条约》规定的通商口岸不包括？', '广州', '上海', '天津', '宁波', '42', 'B');

-- ----------------------------
-- Table structure for n_setting
-- ----------------------------
DROP TABLE IF EXISTS `n_setting`;
CREATE TABLE `n_setting` (
  `key` varchar(50) NOT NULL,
  `value` varchar(5000) DEFAULT '',
  PRIMARY KEY (`key`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of n_setting
-- ----------------------------
INSERT INTO `n_setting` VALUES ('SITE_COPYRIGHT', '&copy;2015');
INSERT INTO `n_setting` VALUES ('SITE_TITLE', '知识竞赛管理平台');

-- ----------------------------
-- Table structure for n_test
-- ----------------------------
DROP TABLE IF EXISTS `n_test`;
CREATE TABLE `n_test` (
  `test_id` int(20) NOT NULL AUTO_INCREMENT,
  `test_name` varchar(20) DEFAULT NULL COMMENT '测试名称',
  `test_time` int(2) DEFAULT NULL COMMENT '测试规定时间',
  `test_par` int(10) DEFAULT NULL COMMENT '参加测试的人数',
  `beizhu` varchar(255) DEFAULT NULL COMMENT '备注',
  `single_code` int(8) DEFAULT NULL COMMENT '每条单选题得分',
  `multy_code` int(8) DEFAULT NULL COMMENT '每条多选题得分',
  PRIMARY KEY (`test_id`)
) ENGINE=MyISAM AUTO_INCREMENT=43 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of n_test
-- ----------------------------
INSERT INTO `n_test` VALUES ('42', '基础知识', '60', null, '', '5', '5');

-- ----------------------------
-- Table structure for n_times
-- ----------------------------
DROP TABLE IF EXISTS `n_times`;
CREATE TABLE `n_times` (
  `username` char(40) NOT NULL,
  `ip` char(15) NOT NULL,
  `logintime` int(10) unsigned NOT NULL DEFAULT '0',
  `isadmin` tinyint(1) NOT NULL DEFAULT '0',
  `times` tinyint(1) unsigned NOT NULL DEFAULT '0',
  PRIMARY KEY (`username`,`isadmin`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of n_times
-- ----------------------------
INSERT INTO `n_times` VALUES ('lijianming', '0.0.0.0', '1440379824', '1', '1');

-- ----------------------------
-- Table structure for n_user
-- ----------------------------
DROP TABLE IF EXISTS `n_user`;
CREATE TABLE `n_user` (
  `user_id` int(64) NOT NULL AUTO_INCREMENT,
  `real_name` varchar(16) DEFAULT NULL COMMENT '真是姓名',
  `stu_id` int(32) DEFAULT NULL COMMENT '学号',
  `idcard` int(32) DEFAULT NULL COMMENT '身份证',
  `college` varchar(32) DEFAULT NULL COMMENT '学院',
  `major` varchar(32) DEFAULT NULL COMMENT '专业',
  `password` varchar(64) DEFAULT NULL COMMENT '密码',
  `qq` int(32) DEFAULT NULL,
  `email` varchar(32) DEFAULT NULL,
  `tel` varchar(32) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=MyISAM AUTO_INCREMENT=37 DEFAULT CHARSET=gbk;

-- ----------------------------
-- Records of n_user
-- ----------------------------
INSERT INTO `n_user` VALUES ('36', 'lcw', '1207300217', '12345', 'jd', 'jk', '12345', '123', '123', '123');

-- ----------------------------
-- Table structure for role_user
-- ----------------------------
DROP TABLE IF EXISTS `role_user`;
CREATE TABLE `role_user` (
  `role_id` mediumint(9) unsigned DEFAULT NULL,
  `user_id` char(32) DEFAULT NULL,
  KEY `group_id` (`role_id`),
  KEY `user_id` (`user_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- ----------------------------
-- Records of role_user
-- ----------------------------
