# GymFitness App
## Overview
GymFitness là một ứng dụng di động được thiết kế để giúp người dùng quản lý hành trình rèn luyện thể chất của mình một cách hiệu quả. Ứng dụng cung cấp cho người dùng các bài tập luyện cá nhân hóa, kế hoạch dinh dưỡng và theo dõi tiến trình, tất cả được tích hợp trong một nền tảng duy nhất. Ứng dụng được phát triển bằng Java, XML và tuân theo kiến trúc MVVM.
### <a href="https://www.mediafire.com/file/6gq8k96488mmofr/gym-fitness.apk/file" target="_blank">Link apk</a>

### Key Features
- **User Authentication**: Đăng nhập 1 lần
- **Workout Plans**: Cung cấp các bài tập cá nhân hóa dựa trên sở thích và mục tiêu của người dùng.
- **Progress Tracking**: Người dùng có thể theo dõi quá trình tập luyện và các cột mốc phát triển thể chất.
- **Exercise Library**: Cung cấp chi tiết về các bài tập với hướng dẫn, hình ảnh và video.
- **Notifications**: Người dùng nhận được thông báo nhắc nhở về các bài tập mỗi ngày và tổng số calo đã đốt cháy trong ngày
# App Preview
### 1.Setup
- Truy cập màn hình "Tạo hồ sơ": Người dùng sẽ bắt đầu bằng cách truy cập vào màn hình tạo hồ sơ cá nhân.
- Nhập thông tin giới tính: Hệ thống sẽ hiển thị màn hình để người dùng chọn giới tính của mình.
- Nhập thông tin tuổi: Tiếp theo, màn hình sẽ yêu cầu người dùng cung cấp thông tin về độ tuổi.
- Nhập thông tin cân nặng: Người dùng sẽ được hướng dẫn nhập cân nặng của mình trên màn hình thu thập dữ liệu.
- Nhập thông tin chiều cao: Hệ thống tiếp tục hiển thị màn hình để người dùng cung cấp thông tin về chiều cao.
- Nhập mục tiêu tập luyện: Cuối cùng, người dùng sẽ được yêu cầu xác định mục tiêu tập luyện của mình.
- Lưu trữ thông tin: Tất cả các thông tin đã thu thập sẽ được lưu vào hồ sơ người dùng mới.
- Điều hướng đến màn hình chính: Sau khi hoàn tất, hệ thống sẽ tự động điều hướng người dùng đến màn hình chính của ứng dụng.
![Untitled](https://github.com/user-attachments/assets/0e34231f-ac7d-4364-9009-b91ea7a9dfc5)
![setup2](https://github.com/user-attachments/assets/58efe24d-9807-453f-8c35-8023d1c728a9)
### 2.Home

- Gợi ý: Cung cấp các bài tập cá nhân hóa dựa trên mục tiêu và sở thích của người dùng.
- Mẹo: Đưa ra những thử thách tập luyện mới mỗi tuần để tạo động lực cho người dùng.
- Tài nguyên: Chứa các video hướng dẫn chi tiết về tập luyện và các bài viết chuyên sâu cung cấp kiến thức về thể thao và sức khỏe.
- Danh sách đã lưu: Hiển thị danh sách các bài tập và bài viết mà người dùng đã lưu để dễ dàng truy cập lại.
- Chatbot: Hỗ trợ người dùng tìm kiếm thông tin và trả lời các câu hỏi liên quan đến tập luyện và sức khỏe.

![home](https://github.com/user-attachments/assets/46b30e1f-6e31-4eda-ba58-6f8beadd7b2b)
### 3.Workout 
Hiển thị các vòng tập (Rounds) cho từng bài tập, với mỗi vòng chứa các video hướng dẫn cho người dùng.
![workout](https://github.com/user-attachments/assets/d7f644ba-15c2-48d4-9d5c-ff1affe62639)

### 4.Article
Hiển thị các bài viết (Articles), cho phép chỉnh sửa thông tin cá nhân và tạo kế hoạch tập luyện (Routine) dựa trên sở thích và mục tiêu của người dùng.
![3](https://github.com/user-attachments/assets/caaac6e3-619d-4b6d-8bb6-ce80b09c07ef).
### 5. Notification Favorites
Hiển thị danh sách thông báo và bài viết yêu thích, cho phép người dùng xóa chúng khi không còn cần thiết.
![list](https://github.com/user-attachments/assets/08d61d25-db51-47df-be8b-45e0aeb59629)

### 6. Search
Cho phép người dùng tìm kiếm tất cả hoặc từng bài tập và bài viết, cũng như xóa lịch sử tìm kiếm.
![search](https://github.com/user-attachments/assets/3fd7868d-ed1e-4a8a-b02b-6e6e6a8aa515)



### Công cụ và thư viện đã dùng
- Navigation Component: Sử dụng một Activity để chứa nhiều Fragment thay vì tạo nhiều Activity riêng lẻ, giúp quản lý điều hướng giữa các phần của ứng dụng dễ dàng.
- MVVM & LiveData: Áp dụng mô hình MVVM để tách biệt logic và giao diện người dùng, LiveData giúp theo dõi và lưu trữ trạng thái dữ liệu một cách an toàn ngay cả khi cấu hình màn hình thay đổi.
- Coroutines: Giúp thực hiện các tác vụ bất đồng bộ trên nền, đảm bảo giao diện người dùng không bị gián đoạn và cải thiện hiệu suất.
- View Binding: Tự động liên kết các view trong layout với mã nguồn, loại bỏ sự cần thiết của phương thức findViewById(), giúp mã nguồn gọn gàng và dễ bảo trì.
- Data Binding: Cho phép liên kết dữ liệu trực tiếp từ mã nguồn vào giao diện người dùng (XML), giảm thiểu việc viết mã xử lý dữ liệu giao diện, giúp ứng dụng phản hồi nhanh hơn.
- Glide: Thư viện hỗ trợ tải và lưu trữ hình ảnh, cho phép hiển thị ảnh một cách mượt mà và tối ưu nhờ vào cơ chế cache ảnh.
- Retrofit: Thư viện mạnh mẽ để thực hiện các yêu cầu HTTP, cho phép kết nối đến các API một cách dễ dàng và thuận tiện.
- Room: Thư viện của Android giúp tương tác với cơ sở dữ liệu SQLite một cách dễ dàng, hỗ trợ truy vấn, cập nhật, và lưu trữ dữ liệu cục bộ trên thiết bị.
- Lottie: Thư viện hỗ trợ việc hiển thị các file hoạt hình JSON, giúp tạo ra các hiệu ứng động mượt mà mà không cần nhiều tài nguyên.
- WorkManager: Quản lý và lên lịch các tác vụ cần thực hiện trong nền, kể cả khi ứng dụng bị đóng hoặc thiết bị khởi động lại.






