package ru.practicum.android.diploma.data.dto

import com.google.gson.annotations.SerializedName

data class VacancyDto(
    @SerializedName("accept_incomplete_resumes")
    val acceptIncompleteResumes: Boolean,
    @SerializedName("accept_temporary")
    val acceptTemporary: Boolean,
    val address: AddressDto,
    // val adv_context: Any,
    // val adv_response_url: Any,
    @SerializedName("alternate_url")
    val alternateUrl: String,
    @SerializedName("apply_alternate_url")
    val applyAlternateUrl: String,
    val archived: Boolean,
    val area: AreaDto,
    val branding: BrandingDto,
    val contacts: ContactsDto,
    @SerializedName("created_at")
    val createdAt: String,
    val department: DepartmentDto,
    val employer: EmployerDto,
    val employment: EmploymentDto,
    val experience: ExperienceDto,
    @SerializedName("has_test")
    val hasTest: Boolean,
    val id: String,
    // val insider_interview: Any,
    @SerializedName("is_adv_vacancy")
    val isAdvVacancy: Boolean,
    val name: String,
    val premium: Boolean,
    @SerializedName("professional_roles")
    val professionalRoles: List<ProfessionalRoleDto>,
    @SerializedName("published_at")
    val publishedAt: String,
    // val relations: List<Any>,
    @SerializedName("response_letter_required")
    val responseLetterRequired: Boolean,
    // val response_url: Any,
    val salary: SalaryDto,
    val schedule: ScheduleDto,
    @SerializedName("show_logo_in_search")
    val showLogoInSearch: Boolean,
    val snippet: SnippetDto,
    // val sort_point_distance: Any,
    val type: TypeDto,
    val url: String,
    // val working_days: List<Any>,
    @SerializedName("working_time_intervals")
    val workingTimeIntervals: List<WorkingTimeIntervalDto>,
    @SerializedName("working_time_modes")
    val workingTimeModes: List<WorkingTimeModeDto>
)
