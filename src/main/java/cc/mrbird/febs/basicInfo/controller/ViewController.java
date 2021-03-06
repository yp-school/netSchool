package cc.mrbird.febs.basicInfo.controller;

import cc.mrbird.febs.basicInfo.entity.*;
import cc.mrbird.febs.basicInfo.service.*;
import cc.mrbird.febs.common.authentication.ShiroHelper;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.entity.FebsConstant;
import cc.mrbird.febs.common.utils.DateUtil;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.utils.HttpUtils;
import cc.mrbird.febs.dingding.config.Constant;
import cc.mrbird.febs.system.entity.User;
import cc.mrbird.febs.system.service.IUserService;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.ExpiredSessionException;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Psy
 */
@Controller("basicInfoView")
public class ViewController extends BaseController {

    @Autowired
    private ISchoolTimetableService schoolTimetableService;

    @Autowired
    private ISchoolService schoolService;

    @Autowired
    private IClassInfoService classInfoService;

    @Autowired
    private IClassroomInfoService classroomInfoService;

    @Autowired
    private ShiroHelper shiroHelper;

    @Autowired
    private ISchoolTeacheinfoService schoolTeacheinfoService;

    @Autowired
    private IPictureNewsService pictureNewsService;

    @Autowired
    private INoticeAnnouncementService noticeAnnouncementService;

    @Autowired
    private INetTimetableService netTimetableService;

    @Autowired
    private IVideoLiveService videoLiveService;

    @Autowired
    private IAlicdnResourceService iAlicdnResourceService;

    //==============================================START==================================================

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/classroomInfo")
    @RequiresPermissions("classroomInfo:view")
    private String classroomIndex(){
        return FebsUtil.view("basicInfo/classroomInfo/classroomInfo");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/classroomInfo/classroomInfoAdd")
    @RequiresPermissions("classroomInfo:add")
    private String classroomAdd(Model model){
        return FebsUtil.view("basicInfo/classroomInfo/classroomInfoAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/classroomInfo/detail/{classroomId}")
    @RequiresPermissions("classroomInfo:view")
    public String classroomInfoDetail(@PathVariable Integer classroomId, Model model) {
        resolveClassrModel(classroomId,model, true);
        return FebsUtil.view("basicInfo/classroomInfo/classroomInfoDetail");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/classroomInfo/update/{classroomInfoId}")
    @RequiresPermissions("classroomInfo:update")
    public String classroomInfoUpdate(@PathVariable Integer classroomInfoId, Model model) {
        resolveClassrModel(classroomInfoId,model, true);
        return FebsUtil.view("basicInfo/classroomInfo/classroomInfoUpdate");
    }

    //==============================================END==================================================

    //==============================================START==================================================

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/classInfo")
    @RequiresPermissions("classInfo:view")
    private String gradeIndex(){
        return FebsUtil.view("basicInfo/classInfo/classInfo");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/classInfo/classInfoAdd")
    @RequiresPermissions("classInfo:add")
    private String gradeAdd(){
        return FebsUtil.view("basicInfo/classInfo/classInfoAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/classInfo/detail/{classInfoId}")
    @RequiresPermissions("classInfo:view")
    public String gradeDetail(@PathVariable Integer classInfoId, Model model) {
        resolveClassModel(classInfoId,model, true);
        return FebsUtil.view("basicInfo/classInfo/classInfoDetail");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/classInfo/update/{classInfoId}")
    @RequiresPermissions("classInfo:update")
    public String gradeUpdate(@PathVariable Integer classInfoId, Model model) {
        resolveClassModel(classInfoId,model, true);
        return FebsUtil.view("basicInfo/classInfo/classInfoUpdate");
    }
    //==============================================END==================================================

    //==============================================START==================================================

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/school")
    @RequiresPermissions("school:view")
    private String schoolIndex(){
        return FebsUtil.view("basicInfo/school/school");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX +  "basicInfo/school/schoolAdd")
    @RequiresPermissions("school:add")
    public String schoolAdd() {
        return FebsUtil.view("basicInfo/school/schoolAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/school/detail/{schoolId}")
    @RequiresPermissions("school:view")
    public String systemUserDetail(@PathVariable Long schoolId, Model model) {
        resolveSchoolrModel(schoolId,model, true);
        return FebsUtil.view("basicInfo/school/schoolDetail");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/school/update/{schoolId}")
    @RequiresPermissions("school:update")
    public String systemUserUpdate(@PathVariable Long schoolId, Model model) {
        resolveSchoolrModel(schoolId,model, true);
        return FebsUtil.view("basicInfo/school/schoolUpdate");
    }

    //==============================================END==================================================

    //==============================================START==================================================

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/area")
    @RequiresPermissions("area:view")
    public String basicInfoArea() {
        return FebsUtil.view("basicInfo/area/area");
    }

    //==============================================END==================================================

    //==============================================START==================================================
    //教师信息
    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/teacherInfo")
    public String basicInfoTeacher() {
        return FebsUtil.view("basicInfo/schoolTeacherinfo/schoolTeacherinfo");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/teacherInfo/teacherInfoAdd")
    private String teacherAdd(){
        return FebsUtil.view("basicInfo/schoolTeacherinfo/schoolTeacherAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/teacherInfo/update/{schoolTeacherinfoId}")
    public String schoolTeacherinfoUpdate(@PathVariable Integer schoolTeacherinfoId, Model model) {
        resolveTeacherModel(schoolTeacherinfoId,model, true);
        return FebsUtil.view("basicInfo/schoolTeacherinfo/schoolTeacherUpdate");
    }
    //==============================================END==================================================

    //==============================================START==================================================
    //通知公告
    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/notice")
    public String basicInfoNotice() {
        return FebsUtil.view("basicInfo/notice/notice");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/notice/noticeAdd")
    private String noticeAdd(){
        return FebsUtil.view("basicInfo/notice/noticeAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/notice/update/{noticeId}")
    public String noticeUpdate(@PathVariable Integer noticeId, Model model) {
        resolveNoticeModel(noticeId,model, true);
        return FebsUtil.view("basicInfo/notice/noticeUpdate");
    }
    //==============================================END==================================================

    //==============================================START==================================================
    //图片新闻
    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/pictureNews")
    public String basicInfoPictureNews() {
        return FebsUtil.view("basicInfo/pictureNews/pictureNews");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/pictureNews/pictureNewsAdd")
    private String pictureNewsAdd(){
        return FebsUtil.view("basicInfo/pictureNews/pictureNewsAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/pictureNews/update/{pictureNewsId}")
    public String pictureNewsUpdate(@PathVariable Integer pictureNewsId, Model model) {
        resolvePictureNewsModel(pictureNewsId,model, true);
        return FebsUtil.view("basicInfo/pictureNews/pictureNewsUpdate");
    }
    //==============================================END==================================================

    //==============================================START==================================================
    //网络课堂
    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/netTimetable")
    public String basicInfoNetTimetable() {
        return FebsUtil.view("basicInfo/netTimetable/netTimetable");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/netTimetable/netTimetableAdd")
    private String netTimetableAdd(){
        return FebsUtil.view("basicInfo/netTimetable/netTimetableAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/netTimetable/update/{netTimetableId}")
    public String netTimetableUpdate(@PathVariable Integer netTimetableId, Model model) {
        resolveNetTimetableModel(netTimetableId,model, true);
        return FebsUtil.view("basicInfo/netTimetable/netTimetableUpdate");
    }
    //==============================================END==================================================

    //==============================================START==================================================
    //网络直播
    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/videoLive")
    public String basicInfoVideoLive() {
        return FebsUtil.view("basicInfo/videoLive/videoLive");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/videoLive/videoLiveAdd")
    private String videoLiveAdd(){
        return FebsUtil.view("basicInfo/videoLive/videoLiveAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/videoLive/update/{liveId}")
    public String videoLiveUpdate(@PathVariable Integer liveId, Model model) {
        resolveVideoLiveModel(liveId,model, true);
        return FebsUtil.view("basicInfo/videoLive/videoLiveUpdate");
    }
    //==============================================END==================================================

    //==============================================START==================================================
    //钉钉新闻推送
    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/alicdnResource")
    public String basicInfoAlicdnResource() {
        return FebsUtil.view("basicInfo/alicdnResource/alicdnResource");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/alicdnResource/alicdnResourceAdd")
    private String AlicdnResourceAdd(){
        return FebsUtil.view("basicInfo/alicdnResource/alicdnResourceAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/alicdnResource/update/{resourceId}")
    public String AlicdnResourceUpdate(@PathVariable Integer resourceId, Model model) {
        resolveAlicdnResourceModel(resourceId,model, true);
        return FebsUtil.view("basicInfo/alicdnResource/alicdnResourceUpdate");
    }
    //==============================================END==================================================


    //==============================================START==================================================

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/schoolTimetable")
    @RequiresPermissions("schoolTimetable:view")
    public String basicInfoSchoolTimetable() {

        return FebsUtil.view("basicInfo/schoolTimetable/schoolTimetable");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/schoolTimetable/add")
    @RequiresPermissions("schoolTimetable:add")
    public String basicInfoSchoolTimetableAdd() {

        return FebsUtil.view("basicInfo/schoolTimetable/schoolTimetableAdd");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/schoolTimetable/update/{courseId}")
    @RequiresPermissions("schoolTimetable:update")
    public String basicInfoSchoolTimetableUpdate(@PathVariable Integer courseId, Model model) throws ParseException {
        resolveSchoolTimetableModel(courseId, model, false);
        return FebsUtil.view("basicInfo/schoolTimetable/schoolTimetableUpdate");
    }

    /**
     * 跳转到预导出接口，让用户选择学校和班级
     * @return
     */
    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/schoolTimetable/toExcel")
    public String toSchoolTimetableExcel() {

        return FebsUtil.view("basicInfo/schoolTimetable/schoolTimetableExportExcel");
    }

    //==============================================END==================================================

    //==============================================START==================================================

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/operate")
    @RequiresPermissions("operate:view")
    public String basicInfoOperate() {
        return FebsUtil.view("basicInfo/operate/operate");
    }

    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/dingRemind")
    public String basicInfodingding() {
        return FebsUtil.view("basicInfo/dingRemind/dingRemind");
    }

    //==============================================END==================================================

    /**
     * 第三方移动应用接入
     * @return
     */
    @GetMapping(FebsConstant.VIEW_PREFIX + "basicInfo/abutment")
    public String basicInfoAbutment() {
        return FebsUtil.view("basicInfo/abutment/abutment");
    }


    //==============================================END==================================================



    private void resolveSchoolTimetableModel(Integer courseId, Model model, Boolean transform) throws ParseException {
        SchoolTimetable schoolTimetable = schoolTimetableService.selectSchooltimetableInfo(courseId);
        model.addAttribute("schoolTimetable", schoolTimetable);
        if (schoolTimetable.getBeginDate() != null)
            model.addAttribute("beginDate", schoolTimetable.getBeginDate());
    }

    private void resolveSchoolrModel(Long schoolId, Model model, Boolean transform) {
        School school = this.schoolService.getById(schoolId);
        model.addAttribute("school", school);
    }

    private void resolveClassrModel(Integer classroomInfoId, Model model, Boolean transform) {
        ClassroomInfo classroomInfo = this.classroomInfoService.getById(classroomInfoId);
        model.addAttribute("classroomInfo", classroomInfo);
    }

    private void resolveTeacherModel(Integer schoolTeacherinfoId, Model model, Boolean transform){
        SchoolTeacheinfo schoolTeacherinfo = this.schoolTeacheinfoService.getById(schoolTeacherinfoId);
        model.addAttribute("schoolTeacherinfo",schoolTeacherinfo);
    }

    private void resolveNoticeModel(Integer noticeId, Model model, Boolean transform){
        NoticeAnnouncement noticeAnnouncement = this.noticeAnnouncementService.getById(noticeId);
        model.addAttribute("notice",noticeAnnouncement);
        if (noticeAnnouncement.getNoticeTime() != null)
            model.addAttribute("specialTime", noticeAnnouncement.getNoticeTime().substring(0,noticeAnnouncement.getNoticeTime().length()-3).replace(" ","T"));
    }

    private void resolvePictureNewsModel(Integer pictureNewsId, Model model, Boolean transform){
        PictureNews pictureNews = this.pictureNewsService.getById(pictureNewsId);
        model.addAttribute("pictureNews",pictureNews);
    }

    private void resolveNetTimetableModel(Integer netTimetableId, Model model, Boolean transform){
        NetTimetable netTimetable = this.netTimetableService.getById(netTimetableId);
        model.addAttribute("netTimetable",netTimetable);
        if (netTimetable.getBeginDate() != null)
            model.addAttribute("specialTime", netTimetable.getBeginDate().substring(0,netTimetable.getBeginDate().length()-3).replace(" ","T"));
    }

    private void resolveVideoLiveModel(Integer videoLiveId, Model model, Boolean transform){
        VideoLive videoLive = this.videoLiveService.getById(videoLiveId);
        model.addAttribute("videoLive",videoLive);
        if (videoLive.getVideoPlayTime() != null)
            model.addAttribute("specialTime", videoLive.getVideoPlayTime().substring(0,videoLive.getVideoPlayTime().length()-3).replace(" ","T"));
    }

    private void resolveAlicdnResourceModel(Integer resourceId, Model model, Boolean transform){
        AlicdnResource alicdnResource = this.iAlicdnResourceService.getById(resourceId);
        model.addAttribute("alicdnResource",alicdnResource);
        if (alicdnResource.getDateTime() != null)
            model.addAttribute("specialTime", alicdnResource.getDateTime().substring(0,alicdnResource.getDateTime().length()-3).replace(" ","T"));
    }

    private void resolveClassModel(Integer classInfoId, Model model, Boolean transform) {
        ClassInfo classInfo = this.classInfoService.getById(classInfoId);
        model.addAttribute("classInfo", classInfo);
    }
}
