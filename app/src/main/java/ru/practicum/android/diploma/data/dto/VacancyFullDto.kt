package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyFullDto(
    @SerializedName("accept_handicapped")
    val acceptHandicapped: Boolean,
    @SerializedName("accept_incomplete_resumes")
    val acceptIncompleteResumes: Boolean,
    @SerializedName("accept_kids")
    val acceptKids: Boolean,
    @SerializedName("accept_temporary")
    val acceptTemporary: Boolean,
    val address: AddressDto,
    @SerializedName("allow_messages")
    val allowMessages: Boolean,
    @SerializedName("alternate_url")
    val alternateUrl: String,
    @SerializedName("apply_alternate_url")
    val applyAlternateUrl: String,
    val approved: Boolean,
    val archived: Boolean,
    val area: AreaDto,
    @SerializedName("billing_type")
    val billingType: BillingTypeDto,
    @SerializedName("branded_description")
    val brandedDescription: Any,
    val code: Any,
    val contacts: ContactsDto,
    @SerializedName("created_at")
    val createdAt: String,
    val department: DepartmentDto,
    val description: String,
    @SerializedName("driver_license_types")
    val driverLicenseTypes: List<Any>,
    val employer: EmployerDto,
    val employment: EmploymentDto,
    val experience: ExperienceDto,
    @SerializedName("has_test")
    val hasTest: Boolean,
    val hidden: Boolean,
    val id: String,
    @SerializedName("initial_created_at")
    val initialCreatedAt: String,
    @SerializedName("insider_interview")
    val insiderInterview: Any,
    @SerializedName("key_skills")
    val keySkills: List<KeySkillDto>,
    val languages: List<Any>,
    val name: String,
    @SerializedName("negotiations_url")
    val negotiationsUrl: Any,
    val premium: Boolean,
    @SerializedName("professional_roles")
    val professionalRoles: List<ProfessionalRoleDto>,
    @SerializedName("published_at")
    val publishedAt: String,
    @SerializedName("quick_responses_allowed")
    val quickResponsesAllowed: Boolean,
    val relations: List<Any>,
    @SerializedName("response_letter_required")
    val responseLetterRequired: Boolean,
    @SerializedName("response_url")
    val responseUrl: Any,
    val salary: SalaryDto,
    val schedule: ScheduleDto,
    val specializations: List<Any>,
    @SerializedName("suitable_resumes_url")
    val suitableResumesUrl: Any,
    val test: Any,
    val type: TypeDto,
    @SerializedName("vacancy_constructor_template")
    val vacancyConstructorTemplate: Any,
    @SerializedName("working_days")
    val workingDays: List<Any>,
    @SerializedName("working_time_intervals")
    val workingTimeIntervals: List<WorkingTimeIntervalDto>,
    @SerializedName("working_time_modes")
    val workingTimeModes: List<WorkingTimeModeDto>
)
